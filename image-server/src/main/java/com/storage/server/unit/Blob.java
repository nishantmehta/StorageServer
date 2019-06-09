package com.storage.server.unit;

import jdk.nashorn.internal.ir.annotations.Immutable;

@Immutable
public class Blob {

    private byte[] data;
    private String name;
    private long length;
    private String mimeType;

    public Blob(byte[] data, String name, long length, String mimeType) {
        this.data = data;
        this.name = name;
        this.length = length;
        this.mimeType = mimeType;
    }

    public byte[] getData() {
        return data;
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
}
