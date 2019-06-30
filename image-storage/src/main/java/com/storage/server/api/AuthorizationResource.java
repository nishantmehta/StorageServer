package com.storage.server.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.storage.server.model.AccessLevel;
import com.storage.server.model.AuthorizationModel;
import com.storage.server.unit.Address;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import java.util.List;

import static com.storage.server.unit.Address.parseAddress;

@Path("/control/")
public class AuthorizationResource {

    private final AuthorizationModel authorizationModel;
    private final ObjectMapper objectMapper = new ObjectMapper();
    public AuthorizationResource(AuthorizationModel authorizationModel) {
        this.authorizationModel = authorizationModel;
    }
    //create access rule

    @POST
    @Path("/{user}/{address}/{accessLevel}")
    public Response createAccessRule(@PathParam("address") String addressRawInput,
                                 @PathParam("user") String user,
                                 @PathParam("accessLevel") String accessLevel) {
        Address address = parseAddress(addressRawInput);
        AccessLevel access = AccessLevel.valueOf(accessLevel);
        if(access == null) throw new IllegalArgumentException("invalid access level provided in request");
        authorizationModel.createAccessRule(address, user, access);
        return Response.status(201).build();
    }

    @GET
    @Path("/{user}/")
    public Response getAllAccessibleAddress(@PathParam("user") String user) throws JsonProcessingException {
        List<Address> addresses = authorizationModel.getAddAvailableAddress(user);
        return Response.ok(objectMapper.writeValueAsString(addresses)).build();
    }


    //check access rule

    //get all available addresses for a user

}
