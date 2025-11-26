package grpc;

import api.UserNetworkAPI;
import impl.CachedComputeEngineAPIImpl;
import impl.DataStorageClient;
import impl.UserNetworkAPIImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

// gRPC server for UserNetworkAPI
public class UserNetworkServer {

    // Main method to start the server
    public static void main(String[] args) throws IOException, InterruptedException {
    DataStorageClient storage = new DataStorageClient("localhost", 50052);
        CachedComputeEngineAPIImpl compute = new CachedComputeEngineAPIImpl();
        // Create the UserNetworkAPI implementation using storage and compute components
        UserNetworkAPI api = new UserNetworkAPIImpl(storage, compute);

        // Build and start the gRPC server
        Server server = ServerBuilder.forPort(50051)
            .addService(new UserNetworkServiceImpl(api))
            .build()
            .start();

        // Print server start message
        System.out.println("Server started on port 50051");
        
        // Add shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down UserNetworkServer.");
            try {
                server.shutdown();
            } catch (Exception e) {
                System.err.println("Error shutting down server: " + e.getMessage());
            }
            try {
                storage.shutdown();
            } catch (Exception e) {
                System.err.println("Error shutting down storage client: " + e.getMessage());
            }
            System.out.println("UserNetworkServer stopped.");
        }));
        
        // Keep the server running until termination
        server.awaitTermination();
    }
}
