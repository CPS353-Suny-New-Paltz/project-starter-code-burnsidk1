package grpc;

import api.NetworkStatusCode;
import api.UserJobStartRequest;
import api.UserJobStartResponse;
import api.UserNetworkAPI;
import io.grpc.stub.StreamObserver;

// gRPC service implementation for UserNetworkAPI
public class UserNetworkServiceImpl extends UserNetworkServiceGrpc.UserNetworkServiceImplBase {

    private final UserNetworkAPI networkApi;

    // Constructor to initialize with UserNetworkAPI implementation
    public UserNetworkServiceImpl(UserNetworkAPI networkApi) {
        this.networkApi = networkApi;
    }

    @Override
    // Implementation of the submitJob RPC method
    public void submitJob(JobRequest request, StreamObserver<JobResponse> responseObserver) {
        // Builds internal request
        UserJobStartRequest internalRequest = new UserJobStartRequest(
            request.getInputLocation(),
            request.getOutputLocation(),
            request.getDelimiter()
        );

        // Call API
        UserJobStartResponse internalResponse = networkApi.submitJob(internalRequest);

        // Get status code
        StatusCode grpcCode = getStatusCode(internalResponse.getCode());

        // Build and send response
        JobResponse grpcResponse = JobResponse.newBuilder()
            .setStatusCode(grpcCode)
            .build();
        responseObserver.onNext(grpcResponse);
        responseObserver.onCompleted();
    }

    // Helper method to get internal status codes as gRPC status codes
    private StatusCode getStatusCode(NetworkStatusCode code) {
        if (code == NetworkStatusCode.SUCCESS) {
            // Get success code
            return StatusCode.SUCCESS;
        }
        if (code == NetworkStatusCode.INVALID_REQUEST) {
            // Get invalid request code
            return StatusCode.INVALID_REQUEST;
        }
        // Default get for any other codes
        return StatusCode.NETWORK_UNAVAILABLE;
    }
}
