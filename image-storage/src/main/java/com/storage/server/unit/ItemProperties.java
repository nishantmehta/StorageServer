package com.storage.server.unit;

import java.io.Serializable;
import java.util.Objects;

public class ItemProperties implements Serializable {
    private String itemId;
    private String name;
    private long length;
    private String mimeType;

    public ItemProperties(String itemId, String name, long length, String mimeType) {
        this.itemId = itemId;
        this.name = name;
        this.length = length;
        this.mimeType = mimeType;
    }

    public String getItemId() {
        return itemId;
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
        ItemProperties that = (ItemProperties) o;
        return length == that.length &&
                Objects.equals(itemId, that.itemId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(mimeType, that.mimeType);
    }

    @Override
    public int hashCode() {

        return Objects.hash(itemId, name, length, mimeType);
    }

    @Override
    public String toString() {
        return "ItemProperties{" +
                "itemId='" + itemId + '\'' +
                ", name='" + name + '\'' +
                ", length=" + length +
                ", mimeType='" + mimeType + '\'' +
                '}';
    }
}
