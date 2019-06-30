package com.storage.server.model.impl;

import com.storage.server.StorageApplication;
import com.storage.server.model.AccessLevel;
import com.storage.server.model.AuthorizationModel;
import com.storage.server.unit.Address;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MemoryAuthorizationModel implements AuthorizationModel {

    private final Map<String, List<Tuple<Address, AccessLevel>>> access = new HashMap<>();
    private final Logger log = Logger.getLogger(StorageApplication.class);

    @Override
    public void createAccessRule(Address address, String user, AccessLevel accessLevel) {
        access.computeIfAbsent(user, key -> new ArrayList<>());
        access.get(user).add(new Tuple<>(address, accessLevel));
    }

    @Override
    public boolean checkAccess(Address address, String user, AccessLevel accessLevel) {
        access.computeIfAbsent(user, key -> new ArrayList<>());
        if(access.containsKey(user)) {
            for(Tuple<Address, AccessLevel> entry : access.get(user)) {
               if(entry.first.equals(address) && (entry.second == AccessLevel.WRITE || entry.second.equals(accessLevel))) return true;
                if(address.isSpecificOrSameAddress(entry.first) && entry.second.equals(accessLevel)) return true;
                log.info(entry.first.getAddressAsString());
            }
        }
        return false;
    }

    @Override
    public List<Address> getAddAvailableAddress(String user) {
        access.computeIfAbsent(user, key -> new ArrayList<>());
        return access.get(user).stream().map(tuple -> tuple.first).collect(Collectors.toList());
    }

    @Override
    public void createUser(String user) {
        access.computeIfAbsent(user, key -> new ArrayList<>());
    }

    private class Tuple<T, R> {
        private T first;
        private R second;

        public Tuple(T first, R second) {
            this.first = first;
            this.second = second;
        }
    }
}
