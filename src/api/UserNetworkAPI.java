package api;

import project.annotations.NetworkAPI;

@NetworkAPI
public interface UserNetworkAPI {
    // The user starts a job
    UserJobStartResponse submitJob(UserJobStartRequest request);
}
