package com.storage.server.model;

import com.storage.server.model.impl.MemoryAuthorizationModel;
import com.storage.server.unit.Address;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class AuthorizationTest {

    private static final String user1 = "user1";
    private static final String user2 = "user2";
    private AuthorizationModel authorizationModel;

    @Before
    public void before() {
        this.authorizationModel = new MemoryAuthorizationModel();
    }


    @Test
    public void createUserAndWarehouse() {
        authorizationModel.createUser(user1);
        authorizationModel.createUser(user2);

        authorizationModel.createAccessRule(new Address(user1, "NewYorkWareHouse", "SportsPhotos"), user1, AccessLevel.WRITE);
        authorizationModel.createAccessRule(new Address(user1,"NewYorkWareHouse", "FriendsPhotos"), user1, AccessLevel.WRITE);
        authorizationModel.createAccessRule(new Address(user1, "NewYorkWareHouse", "Random"), user1, AccessLevel.WRITE);
        authorizationModel.createAccessRule(new Address(user1, "NewYorkWareHouse"), user2, AccessLevel.READ);

        assertTrue("access level incorrect", authorizationModel.getAddAvailableAddress(user2).size() == 1);
        assertTrue("Allowed access denied by model",
                authorizationModel.checkAccess(new Address(user1,"NewYorkWareHouse", "SportsPhotos"),
                        user2,
                        AccessLevel.READ));

    }

    @Test
    public void listAllAvailableAddressForUser() {
        authorizationModel.createUser(user1);
        authorizationModel.createUser(user1);

        authorizationModel.createAccessRule(new Address(user1, "NewYorkWareHouse", "SportsPhotos"), user1, AccessLevel.WRITE);

        authorizationModel.createAccessRule(new Address(user1, "NewYorkWareHouse"), user2, AccessLevel.READ);
        authorizationModel.createAccessRule(new Address(user2, "NewJerseyWareHouse"), user2, AccessLevel.WRITE);

        assertTrue("access level incorrect", authorizationModel.getAddAvailableAddress(user2).size() == 2);
    }
}
