package client;

import grpc.JobRequest;
import grpc.JobResponse;
import grpc.UserNetworkServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.Scanner;

public class UserNetworkClient {

    // Main method to run the client
    public static void main(String[] args) {
        String host = "localhost";
        int port = 50051;
        String inputLocation;
        String outputLocation;
        String delimiter;

        // If no arguments entered, use Scanner mode, else use command line
        if (args.length == 0) {
            Scanner scanner = new Scanner(System.in);
            
            // Prompt user for input location
            System.out.print("Enter input (file path or comma-separated numbers): ");
            inputLocation = scanner.nextLine().trim();
            
            // Prompt user for output location and delimiter
            System.out.print("Enter output file path: ");
            outputLocation = scanner.nextLine().trim();
            
            // Prompt user for delimiter
            System.out.print("Enter delimiter (default: |): ");
            String delimiterInput = scanner.nextLine().trim();
            delimiter = delimiterInput.isEmpty() ? "|" : delimiterInput;

            // Close the scanner
            scanner.close();

        // If command-line arguments used because argument length is at least 2
        } else if (args.length >= 2) {
            inputLocation = args[0];
            outputLocation = args[1];
            // | is the default delimiter
            delimiter = args.length >= 3 ? args[2] : "|";

        } else {
            // Print usage message for incorrect arguments
            System.err.println("<inputLocation> <outputLocation> <delimiter>");
            System.err.println("Or run with no arguments for Scanner mode.");
            System.exit(1);
            return;
        }

        // Create gRPC channel to connect to server
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
            .usePlaintext()
            .build();

        // Create blocking stub for making calls
        UserNetworkServiceGrpc.UserNetworkServiceBlockingStub stub =
            UserNetworkServiceGrpc.newBlockingStub(channel);

        // Build the job request
        JobRequest request = JobRequest.newBuilder()
            .setInputLocation(inputLocation)
            .setOutputLocation(outputLocation)
            .setDelimiter(delimiter)
            .build();

        // Make the RPC call to submit the job
        JobResponse response = stub.submitJob(request);

        // Print the status code from the response
        System.out.println("Status: " + response.getStatusCode());

        // Shutdown the gRPC channel
        channel.shutdown();
    }
}
