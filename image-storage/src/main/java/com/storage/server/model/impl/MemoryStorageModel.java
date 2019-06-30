package com.storage.server.model.impl;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import com.storage.server.model.StorageModel;
import com.storage.server.unit.Address;
import com.storage.server.unit.Item;
import com.storage.server.unit.ItemProperties;
import com.storage.server.unit.Layout;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.Logger;

import javax.ws.rs.InternalServerErrorException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.CREATE_NEW;


public class MemoryStorageModel implements StorageModel {

    Logger log = Logger.getLogger(MemoryStorageModel.class);
    private final Map<String, byte[]> blobstore = new HashMap<>();
    private final FileSystem fs = Jimfs.newFileSystem(Configuration.unix());

    @Override
    public void createAddress(Address address) {
        Path path = fs.getPath(address.getUnixAddress());
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createItem(Address address, Item item) {
        blobstore.put(item.getItemProperties().getItemId(), item.getBytes());
        Path file = fs.getPath(address.getUnixAddress()).resolve(item.getItemProperties().getItemId());

        try {
            byte[] data = SerializationUtils.serialize(item.getItemProperties());
            Files.write(file, data, CREATE_NEW);
        } catch (IOException e) {
            throw new InternalServerErrorException(e);
        }
    }

    @Override
    public Optional<Item> getItem(Address address, String itemId) {
        Path file = fs.getPath(address.getUnixAddress()).resolve(itemId);
        try {
            byte[] bytes = Files.readAllBytes(file);
            ItemProperties itemProperties = (ItemProperties) SerializationUtils.deserialize(bytes);
            return Optional.of(new Item(blobstore.get(itemId), itemProperties));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Layout getLayout(Address address) {
        Path dir = fs.getPath(address.getUnixAddress());
        try (DirectoryStream<Path> stream =
                     Files.newDirectoryStream(dir, "*")) {
            List<Address> addressList = new ArrayList<>();
            List<ItemProperties> itemProperties = new ArrayList<>();
            for (Path entry: stream) {
                if(Files.isDirectory(entry)) {
                    addressList.add(address.createSpecificAddress(entry.getFileName().toString()));
                } else {
                    byte[] bytes = Files.readAllBytes(entry);
                    itemProperties.add((ItemProperties) SerializationUtils.deserialize(bytes));
                }
            }
            return new Layout(addressList, itemProperties);
        } catch (IOException x) {
            // IOException can never be thrown by the iteration.
            // In this snippet, it can // only be thrown by newDirectoryStream.
            System.err.println(x);
        }
        return null;
    }







    /*
    Logger log = Logger.getLogger(StorageApplication.class);

    private final Map<String, Blob> blobstore = new HashMap<>();

    private BiFunction<String, String, String> getDirName = (dir, name) -> dir + "/" + name;
    public MemoryStorageModel() {
        blobstore.put("healthCheckImage.png", new Blob("this is image".getBytes(), "healthCheckImage.png",
                30, "image"));

        try {
            FileInputStream ocean = new FileInputStream("src/main/resources/images/ocean.jpg");
            FileInputStream sunset = new FileInputStream("src/main/resources/images/sunset.jpg");
            FileInputStream venice = new FileInputStream("src/main/resources/images/venice.jpg");

            createBlob(new  Blob(StorageServerResource.convertStreamToArray(ocean), "ocean.jpg",
                    30, "image/jpg"), "private");
            createBlob(new  Blob(StorageServerResource.convertStreamToArray(sunset), "sunset.jpg",
                    30, "image/jpg"), "nishant.india");
            createBlob(new  Blob(StorageServerResource.convertStreamToArray(venice), "venice.jpg",
                    30, "image/jpg"), "nishant.vacation");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int createDirectory(String directory) {
        return 0;
    }

    @Override
    public int createBlob(Blob blob, String directory) {
        blobstore.put(getDirName.apply(directory, blob.getName()), blob);
        return 0;
    }

    @Override
    public Optional<Blob> getBlob(String name, String directory) {
        log.info(String.format("name %s and path %s requested", name, directory));
        if(blobstore.containsKey(getDirName.apply(directory, name))) {
            return Optional.of(blobstore.get(getDirName.apply(directory, name)));
        }
        return Optional.empty();
    }

    @Override
    public int createFolder(Directory root, String folder) {
        return 0;
    }

    @Override
    public int createBlob(Directory directory, Blob blob) {
        return 0;
    }

    @Override
    public Optional<Blob> getBlob(String directory, Blob blob) {
        return Optional.empty();
    }

    @Override
    public List<Directory> getDirectory(String directory) {
        return null;
    }
    */
}
