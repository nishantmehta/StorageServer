package com.storage.server.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.storage.server.StorageApplication;
import com.storage.server.model.AccessLevel;
import com.storage.server.model.AuthorizationModel;
import com.storage.server.model.StorageModel;
import com.storage.server.unit.Address;
import com.storage.server.unit.Item;
import com.storage.server.unit.ItemProperties;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Path("/{user}/")
public class StorageServerResource {
    Logger log = Logger.getLogger(StorageApplication.class);

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final StorageModel storageModel;
    private final AuthorizationModel authorizationModel;

    public StorageServerResource(StorageModel storageModel, AuthorizationModel authorizationModel) {
        this.storageModel = storageModel;
        this.authorizationModel = authorizationModel;
    }


    //show content of this address

    //get specific file

    //add file to this address

    //get transformation on file

    @POST
    @Path("/{address}")
    public Response createAddress(@PathParam("address") String addressString) {
        Address address = Address.parseAddress(addressString);
        storageModel.createAddress(address);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{address}/{key}")
    @Produces(MediaType.WILDCARD)
    public Response getImage(@PathParam("user") String user,
                             @PathParam("key") String key,
                             @PathParam("address") String addressInput) {
        Address address = Address.parseAddress(addressInput);
        if(!authorizationModel.checkAccess(address, user, AccessLevel.READ)) return Response.status(403).build();
        log.info(String.format("requesting blob %s %s for user %s", address.getAddressAsString(), key ,user));
        Optional<Item> item = storageModel.getItem(address, key);
        if(item.isPresent())
            return Response.ok(new ByteArrayInputStream(item.get().getBytes())).header("Content-Type", item.get().getItemProperties().getMimeType()).build();
        return Response.status(404).build();
    }

    @POST
    @Path("/{address}/{key}")
    @Consumes(MediaType.WILDCARD)
    public Response createImage(@HeaderParam("Content-Type") String contentType,
                                @PathParam("address") String addressString,
                                @PathParam("key") String key,
                                @PathParam("user") String user,
                                InputStream inputStream) throws IOException {
        Address address = Address.parseAddress(addressString);
        if(!authorizationModel.checkAccess(address, user, AccessLevel.WRITE)) return Response.status(403).build();
        log.info(String.format("inserting blob %s %s for user %s", address.getAddressAsString(), key ,user));
        byte[] bytes = convertStreamToArray(inputStream);
        Item item = new Item(bytes, new ItemProperties(UUID.randomUUID().toString().replace("-", ""), key, bytes.length, contentType));
        storageModel.createItem(address, item);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{address}")
    public Response getDirectory(@PathParam("user") String user,
                                 @PathParam("address") String addressString) throws JsonProcessingException {
        Address address = Address.parseAddress(addressString);
        if(!authorizationModel.checkAccess(address, user, AccessLevel.READ)) return Response.status(403).build();
        return Response.ok(objectMapper.writeValueAsString(storageModel.getLayout(address))).build();

    }

    public static byte[] convertStreamToArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        return buffer.toByteArray();
    }



}
