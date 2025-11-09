package grpc.storage;

import api.DataStorageAPI;
import impl.DataStorageAPIImpl;
import impl.FileDataStore;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class DataStorageServer {
    public static void main(String[] args) throws Exception {
        // Initialize the DataStorageAPI implementation
        DataStorageAPI storage = new DataStorageAPIImpl(new FileDataStore());
        // Start the gRPC server
        Server server = ServerBuilder.forPort(50052)
                .addService(new DataStorageServiceImpl(storage))
                .build()
                .start();
        System.out.println("DataStorage gRPC server started on port 50052");
        // Shutting down
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("DataStorageServer shutting down.");
            // Shutdown the gRPC server
        try {
            server.shutdown();
            } catch (Exception e) {
                System.err.println("Error shutting down server: " + e.getMessage());
            }
            System.out.println("DataStorageServer stopped.");
        }));
        // Keep the server running until termination
        server.awaitTermination();
    }
}
