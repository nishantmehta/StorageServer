package com.storage.server.unit;

import java.util.List;
import java.util.Objects;

public class Directory {

    private final List<Directory> subDirectory;
    private final List<BlobProperties> blobProperties;

    public Directory(List<Directory> subDirectory, List<BlobProperties> blobProperties) {
        this.subDirectory = subDirectory;
        this.blobProperties = blobProperties;
    }

    public List<Directory> getSubDirectory() {
        return subDirectory;
    }

    public List<BlobProperties> getBlobProperties() {
        return blobProperties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Directory directory = (Directory) o;
        return Objects.equals(subDirectory, directory.subDirectory) &&
                Objects.equals(blobProperties, directory.blobProperties);
    }

    @Override
    public int hashCode() {

        return Objects.hash(subDirectory, blobProperties);
    }

    @Override
    public String toString() {
        return "Directory{" +
                "subDirectory=" + subDirectory +
                ", blobProperties=" + blobProperties +
                '}';
    }
}
