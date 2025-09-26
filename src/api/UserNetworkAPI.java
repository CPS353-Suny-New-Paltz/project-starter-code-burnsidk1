package api;

import project.annotations.NetworkAPI;
// Comment to see if this fixes Gradle issue from Checkpoint3
@NetworkAPI
public interface UserNetworkAPI {
    // The user starts a job
    UserJobStartResponse submitJob(UserJobStartRequest request);
}
