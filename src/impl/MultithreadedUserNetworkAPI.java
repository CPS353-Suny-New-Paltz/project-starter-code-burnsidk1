package impl;

import api.UserNetworkAPI;
import api.DataStorageAPI;
import api.ComputeEngineAPI;
import api.InputBatch;
import java.util.List;

// Created class extending AbstractUserNetworkAPI for Multithreaded implementation
public class MultithreadedUserNetworkAPI extends AbstractUserNetworkAPI implements UserNetworkAPI {

    public MultithreadedUserNetworkAPI(DataStorageAPI dataStorageApi, ComputeEngineAPI computeEngineApi) {
        super(dataStorageApi, computeEngineApi);
    }

    @Override
    protected List<String> processInputBatch(InputBatch batch) {
        return null;
    }
}