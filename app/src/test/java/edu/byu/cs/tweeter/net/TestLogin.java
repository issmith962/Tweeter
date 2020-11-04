package edu.byu.cs.tweeter.net;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.Client.net.ServerFacade;
import byu.edu.cs.tweeter.shared.domain.Follow;
import byu.edu.cs.tweeter.shared.domain.Status;
import byu.edu.cs.tweeter.shared.domain.User;
import byu.edu.cs.tweeter.shared.request.LoginRequest;
import byu.edu.cs.tweeter.shared.request.RegisterRequest;
import byu.edu.cs.tweeter.shared.request.StartUpRequest;
import byu.edu.cs.tweeter.shared.response.LoginResponse;
import byu.edu.cs.tweeter.shared.response.RegisterResponse;
import byu.edu.cs.tweeter.shared.response.StartUpResponse;
import edu.byu.cs.tweeter.Client.presenter.LoginPresenter;

public class TestLogin implements LoginPresenter.View {
    LoginPresenter presenter;
    ServerFacade serverFacade;
    List<Follow> follows;
    Map<User,List<Status>> statusesByUser;
    Map<User,String> passwordsByUser;
    @BeforeEach
    void setup() {
        //View view = Mockito.mock(View.class);
        serverFacade = new ServerFacade();
        presenter = new LoginPresenter(this);
        follows = new ArrayList<>();
        statusesByUser = new HashMap<>();
        passwordsByUser = new HashMap<>();
        //serverFacade.testInitialize(follows,statusesByUser,passwordsByUser);
    }

    @Test
    void testStartUpPositive() {
        StartUpResponse response = presenter.startUp(new StartUpRequest());
        assertEquals(response.getMessage(), "Success!");
    }

    @Test
    void testRegisterUserPositive() {
        serverFacade.testInitialize(follows,statusesByUser,passwordsByUser);
        RegisterRequest request = new RegisterRequest("isaac smith", "@isaac", "passyword", null);
        RegisterResponse response = presenter.registerUser(request);
        assertNotNull(response);
        assertEquals(request.getAlias(), response.getNewUser().getAlias());
        assertEquals(request.getName(), response.getNewUser().getName());
        assertEquals(request.getPassword(), response.getPassword());
        assertEquals(request.getImageUri(), response.getNewUser().getImageUri());
    }

    @Test // name property only has one, not a first AND last
    void testRegisterUserNegative() {
        serverFacade.testInitialize(follows,statusesByUser,passwordsByUser);
        RegisterRequest request = new RegisterRequest("isaacsmith", "@isaac", "passyword", null);
        RegisterResponse response = presenter.registerUser(request);
        assertNull(response.getNewUser());
        assertNotNull(response.getPassword());
    }

    @Test
    void testCheckLoginPositive() {
        final User user1 = new User("Daffy", "Duck", "");
        passwordsByUser.put(user1, "goodpassword");
        List<User> allUsers = new ArrayList<>();
        allUsers.add(user1);
        serverFacade.testInitialize(follows,statusesByUser,passwordsByUser, allUsers);

        LoginRequest request = new LoginRequest("@DaffyDuck", "goodpassword");
        LoginResponse response = presenter.checkLogin(request);

        assertNotNull(response.getAuthToken());
        assertNotEquals(response.getAuthToken(), "");
        assertEquals(response.getAlias(), user1.getAlias());
        assertEquals(response.getFirstName(), user1.getFirstName());
        assertEquals(response.getLastName(), user1.getLastName());
        assertEquals(response.getImageUri(), user1.getImageUri());
        assertEquals(response.getImageURL(), user1.getImageUrl());
    }

    @Test
    void testCheckLoginNegative() {
        serverFacade.testInitialize(follows,statusesByUser,passwordsByUser);
        LoginRequest request = new LoginRequest("@DaffyDuck", "goodpassword");
        LoginResponse response = presenter.checkLogin(request);

        assertNull(response.getAuthToken());
        assertNull(response.getAlias());
        assertNull(response.getFirstName());
        assertNull(response.getImageUri());
        assertNull(response.getImageURL());
        assertNull(response.getPassword());
        assertNull(response.getLastName());
    }




}