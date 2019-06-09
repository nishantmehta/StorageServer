package com.storage.server.model.impl;

import com.storage.server.model.StorageModel;
import com.storage.server.unit.Blob;

public class MemoryStorageModel implements StorageModel {


    @Override
    public int createDirectory(String directory) {
        return 0;
    }

    @Override
    public int createBlob(Blob blob, String directory) {
        return 0;
    }

    @Override
    public Blob getBlob(String name, String directory) {
        return null;
    }
}
