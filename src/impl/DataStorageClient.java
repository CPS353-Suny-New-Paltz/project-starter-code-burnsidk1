package impl;

import api.DataStorageAPI;
import api.InputBatch;
import api.StorageWriteRequest;
import api.StorageWriteResponse;
import api.StorageStatusCode;
import api.WriteResult;

import grpc.storage.DataStorageServiceGrpc;
import grpc.storage.ReadInputsRequest;
import grpc.storage.ReadInputsResponse;
import grpc.storage.WriteOutputsRequest;
import grpc.storage.WriteOutputsResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.List;

// Client implementation of DataStorageAPI using gRPC
public class DataStorageClient implements DataStorageAPI {

    private final ManagedChannel channel;
    private final DataStorageServiceGrpc.DataStorageServiceBlockingStub stub;

    // Constructor to initialize gRPC channel and blocking stub
    public DataStorageClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        this.stub = DataStorageServiceGrpc.newBlockingStub(channel);
    }

    // Shutdown the gRPC channel
    public void shutdown() {
        try {
        channel.shutdown();
        } catch (Exception e) {
            // Cleanup code
            System.err.println("Error shutting down DataStorage channel: " + e.getMessage());
        }
    }

    // Read inputs from the gRPC server
    @Override
    public InputBatch readInputs(String inputLocation) {
        // If the input location is null, returns null
        if (inputLocation == null || inputLocation.trim().isEmpty()) {
            return null;
        }
        try {
        // Make gRPC call to readInputs
        ReadInputsResponse resp = stub.readInputs(ReadInputsRequest.newBuilder()
                .setInputLocation(inputLocation)
                .build());
        List<Integer> values = new ArrayList<>(resp.getValuesCount());
        values.addAll(resp.getValuesList());
        // Returns the InputBatch with the retrieved values
        return new InputBatch(values);
        } catch (Exception e) {
        // Return error on gRPC error
        throw new RuntimeException("Failed to read inputs from storage server: " + e.getMessage(), e);
        }
    }

    // Write outputs to the gRPC server
    @Override
    public WriteResult writeOutputs(String outputLocation, List<String> formattedPairs) {
        // If the output location or formatted pairs are invalid, returns invalid arguments
        if (outputLocation == null || outputLocation.trim().isEmpty() || formattedPairs == null || formattedPairs.isEmpty()) {
            return new WriteResult(false, "Invalid arguments");
        }
        try {
        // Make gRPC call to writeOutputs
        WriteOutputsResponse resp = stub.writeOutputs(WriteOutputsRequest.newBuilder()
                .setOutputLocation(outputLocation)
                .addAllFormattedPairs(formattedPairs)
                .build());
        // Returns the WriteResult based on the response
        return new WriteResult(resp.getSuccess(), resp.getMessage());
        } catch (Exception e) {
            // Return failure
            return new WriteResult(false, "gRPC error: " + e.getMessage());
        }
    }

    // Handles write requests
    @Override
    public StorageWriteResponse writeResults(StorageWriteRequest request) {
        // If the request or destination is null, returns INVALID_REQUEST
        if (request == null || request.getDestination() == null) {
            return new StorageWriteResponse(StorageStatusCode.INVALID_REQUEST);
        }
        // Calls writeOutputs with the request data
        String out = request.getDestination().getLocation();
        WriteResult r = writeOutputs(out, request.getFormattedPairs());
        // Returns the StorageWriteResponse based on the WriteResult
        return new StorageWriteResponse(r != null && r.success() ? StorageStatusCode.SUCCESS : StorageStatusCode.STORAGE_UNAVAILABLE);
    }
}
