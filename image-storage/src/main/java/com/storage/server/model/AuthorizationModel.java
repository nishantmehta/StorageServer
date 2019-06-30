package com.storage.server.model;

import com.storage.server.unit.Address;

import java.util.List;

public interface AuthorizationModel {

    //create access rule
    void createAccessRule(Address address, String user, AccessLevel accessLevel);

    //check access rule
    boolean checkAccess(Address address, String user, AccessLevel accessLevel);

    //get all available addresses for a user
    List<Address> getAddAvailableAddress(String user);

    void createUser(String user);
}
