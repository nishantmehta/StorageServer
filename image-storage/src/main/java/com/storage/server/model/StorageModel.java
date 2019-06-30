package com.storage.server.model;

import com.storage.server.unit.Address;
import com.storage.server.unit.Item;
import com.storage.server.unit.Layout;

import java.util.Optional;

public interface StorageModel {
    void createAddress(Address address);
    void createItem(Address address, Item item);
    Optional<Item> getItem(Address address, String itemId);
    Layout getLayout(Address address);
}
