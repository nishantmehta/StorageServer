package com.storage.server;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.apache.log4j.Logger;

public class StorageApplication extends Application<StorageServerConfig> {
    Logger log = Logger.getLogger(StorageApplication.class);
    @Override
    public void run(StorageServerConfig configuration, Environment environment) throws Exception {
        log.info("Running starting image storage server");

    }

    public static void main (String args[]) throws Exception {
        new StorageApplication().run(args);
    }
}