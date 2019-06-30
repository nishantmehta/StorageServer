package com.storage.server.unit;

import java.util.Objects;

public class BlobProperties {
    private String name;
    private long length;
    private String mimeType;

    public BlobProperties(String name, long length, String mimeType) {
        this.name = name;
        this.length = length;
        this.mimeType = mimeType;
    }

    public String getName() {
        return name;
    }

    public long getLength() {
        return length;
    }

    public String getMimeType() {
        return mimeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlobProperties that = (BlobProperties) o;
        return length == that.length &&
                Objects.equals(name, that.name) &&
                Objects.equals(mimeType, that.mimeType);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, length, mimeType);
    }

    @Override
    public String toString() {
        return "BlobProperties{" +
                "name='" + name + '\'' +
                ", length=" + length +
                ", mimeType='" + mimeType + '\'' +
                '}';
    }
}
