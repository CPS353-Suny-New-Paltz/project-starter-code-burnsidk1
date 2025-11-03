package grpc;

import api.UserNetworkAPI;
import impl.ComputeEngineAPIImpl;
import impl.DataStorageAPIImpl;
import impl.UserNetworkAPIImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

// gRPC server for UserNetworkAPI
public class UserNetworkServer {

    // Main method to start the server
    public static void main(String[] args) throws IOException, InterruptedException {
        DataStorageAPIImpl storage = new DataStorageAPIImpl();
        ComputeEngineAPIImpl compute = new ComputeEngineAPIImpl();
        // Create the UserNetworkAPI implementation using storage and compute components
        UserNetworkAPI api = new UserNetworkAPIImpl(storage, compute);

        // Build and start the gRPC server
        Server server = ServerBuilder.forPort(50051)
            .addService(new UserNetworkServiceImpl(api))
            .build()
            .start();

        // Print server start message
        System.out.println("Server started on port 50051");
        // Keep the server running until termination
        server.awaitTermination();
    }
}
