package com.storage.server.model;

import com.storage.server.model.impl.MemoryStorageModel;
import com.storage.server.unit.Address;
import com.storage.server.unit.Item;
import com.storage.server.unit.ItemProperties;
import com.storage.server.unit.Layout;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;

public class StorageModelTest {


    StorageModel storageModel = new MemoryStorageModel();

    @Test
    public void testBasicInput() {
        byte[] content = "this is a sample blob".getBytes();
        storageModel.createAddress(new Address("user1"));
        storageModel.createAddress(new Address("user1", "newyork"));
        Address address = new Address("user1", "newyork", "2019");
        storageModel.createAddress(address);
        storageModel.createItem(address, new Item(content, new ItemProperties("123", "sample.png", content.length,  MediaType.APPLICATION_OCTET_STREAM)));
        Optional<Item> item = storageModel.getItem(address, "123");
        assertTrue("not found expected item", item.isPresent());
        assertTrue("did not find the correct item", new String(content).equalsIgnoreCase(new String(item.get().getBytes())));
    }


    @Test
    public void testGetLayout() {
        String user2 = "user2";
        byte[] content = "this is a sample blob".getBytes();
        storageModel.createAddress(new Address(user2));
        storageModel.createAddress(new Address(user2, "newyork"));
        Address address = new Address(user2, "newyork", "2019");
        storageModel.createAddress(address);
        storageModel.createAddress(new Address(user2, "newyork", "2019", "picnic"));
        storageModel.createItem(address, new Item(content, new ItemProperties("124", "sample.png", content.length,  MediaType.APPLICATION_OCTET_STREAM)));
        storageModel.createItem(address, new Item(content, new ItemProperties("125", "sample.png", content.length,  MediaType.APPLICATION_OCTET_STREAM)));
        Layout layout = storageModel.getLayout(address);
        assertTrue("expecting one sub-directory", layout.getAddresses().size() == 1);
        assertTrue("expecting two files", layout.getFiles().size() == 2);

    }




}
