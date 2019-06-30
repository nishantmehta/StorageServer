package com.storage.server.unit;

import java.util.Arrays;
import java.util.Objects;

public class Item {

    private final byte[] bytes;
    private final ItemProperties itemProperties;

    public Item(byte[] bytes, ItemProperties itemProperties) {
        this.bytes = bytes;
        this.itemProperties = itemProperties;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public ItemProperties getItemProperties() {
        return itemProperties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Arrays.equals(bytes, item.bytes) &&
                Objects.equals(itemProperties, item.itemProperties);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(itemProperties);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "byteArrayLength=" + bytes.length +
                ", itemProperties=" + itemProperties +
                '}';
    }
}
