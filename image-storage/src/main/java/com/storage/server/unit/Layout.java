package com.storage.server.unit;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Layout {

    private final List<Address> addresses;
    private final List<ItemProperties> items;

    public Layout(List<Address> addresses, List<ItemProperties> items) {
        Objects.requireNonNull(addresses);
        Objects.requireNonNull(items);
        this.addresses = addresses;
        this.items = items;
    }

    public List<Address> getAddresses() {
        return Collections.unmodifiableList(addresses);
    }

    public List<ItemProperties> getFiles() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public String toString() {
        return "Layout{" +
                "addresses=" + addresses +
                ", items=" + items +
                '}';
    }
}
