package com.storage.server;

import com.storage.server.api.AuthorizationResource;
import com.storage.server.api.StorageServerResource;
import com.storage.server.model.AuthorizationModel;
import com.storage.server.model.StorageModel;
import com.storage.server.model.impl.MemoryAuthorizationModel;
import com.storage.server.model.impl.MemoryStorageModel;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.apache.log4j.Logger;

public class StorageApplication extends Application<StorageServerConfig> {
    private final Logger log = Logger.getLogger(StorageApplication.class);
    @Override
    public void run(StorageServerConfig configuration, Environment environment) {
        log.info("Running starting image storage server");
        StorageModel storageModel = new MemoryStorageModel();
        AuthorizationModel authorizationModel = new MemoryAuthorizationModel();
        environment.jersey().register(new StorageServerResource(storageModel, authorizationModel));
        environment.jersey().register(new AuthorizationResource(authorizationModel));
    }

    public static void main (String args[]) throws Exception {
        new StorageApplication().run(args);
    }
}