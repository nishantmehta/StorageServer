package com.storage.server.model;

import com.storage.server.unit.Blob;

public interface StorageModel {

    //create directory
    int createDirectory(String directory);

    //create blob inside dir
    int createBlob(Blob blob, String directory);

    //get blob from dir
    Blob getBlob(String name, String directory);
}
