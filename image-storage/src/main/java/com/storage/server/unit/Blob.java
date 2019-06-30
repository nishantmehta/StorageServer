package com.storage.server.unit;


import java.util.Arrays;
import java.util.Objects;


public class Blob {

    private byte[] data;
    private BlobProperties blobProperties;

    public Blob(byte[] data, BlobProperties blobProperties) {
        this.data = data;
        this.blobProperties = blobProperties;
    }

    public byte[] getData() {
        return data;
    }

    public BlobProperties getBlobProperties() {
        return blobProperties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blob blob = (Blob) o;
        return Arrays.equals(data, blob.data) &&
                Objects.equals(blobProperties, blob.blobProperties);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(blobProperties);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "Blob{" +
                "data=" + Arrays.toString(data) +
                ", blobProperties=" + blobProperties +
                '}';
    }
}
