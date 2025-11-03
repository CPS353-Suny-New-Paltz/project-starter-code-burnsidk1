package grpc.storage;

import api.DataStorageAPI;
import api.InputBatch;
import api.WriteResult;
import grpc.storage.DataStorageServiceGrpc.DataStorageServiceImplBase;
import io.grpc.stub.StreamObserver;

// gRPC service implementation for DataStorageAPI
public class DataStorageServiceImpl extends DataStorageServiceImplBase {

    private final DataStorageAPI storageApi;

    // Constructor to initialize with DataStorageAPI implementation
    public DataStorageServiceImpl(DataStorageAPI storageApi) {
        this.storageApi = storageApi;
    }

    @Override
    // Implementation of the readInputs gRPC method
    public void readInputs(ReadInputsRequest request, StreamObserver<ReadInputsResponse> responseObserver) {
        InputBatch batch = storageApi.readInputs(request.getInputLocation());
        ReadInputsResponse.Builder builder = ReadInputsResponse.newBuilder();
        if (batch != null && batch.values() != null) {
            for (Integer value : batch.values()) {
                if (value != null) {
                    builder.addValues(value);
                }
            }
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    // Implementation of the writeOutputs gRPC method
    public void writeOutputs(WriteOutputsRequest request, StreamObserver<WriteOutputsResponse> responseObserver) {
        WriteResult result = storageApi.writeOutputs(request.getOutputLocation(), request.getFormattedPairsList());
        WriteOutputsResponse response = WriteOutputsResponse.newBuilder()
                .setSuccess(result != null && result.success())
                .setMessage(result == null ? "" : result.message())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
