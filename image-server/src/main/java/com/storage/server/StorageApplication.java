package com.storage.server;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class StorageApplication extends Application<StorageServerConfig> {

    @Override
    public void run(StorageServerConfig configuration, Environment environment) throws Exception {
        System.out.println("Running starting image storage server");
    }

    public static void main (String args[]) throws Exception {
        new StorageApplication().run(args);
    }
}