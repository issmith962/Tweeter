package edu.byu.cs.tweeter.net;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.Status;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.LoginRequest;
import byu.edu.cs.tweeter.shared.response.LoginResponse;
import edu.byu.cs.tweeter.Client.model.services.LoginServiceProxy;
import edu.byu.cs.tweeter.Client.net.ServerFacade;

public class LoginServiceProxyTest {
    private LoginRequest validRequest;
    private LoginRequest invalidRequest;

    private LoginResponse successResponse;
    private LoginResponse failureResponse;

    private LoginServiceProxy feedServiceProxySpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);
        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        Status user1Status = new Status(resultUser1, "01/01/2001", "status text");
        Status user2Status = new Status(resultUser2, "02/02/2002", "status text");
        Status user3Status = new Status(resultUser3, "03/03/2003", "status text");

        validRequest = new LoginRequest("alias", "password");
        invalidRequest = new LoginRequest("alias", null);

        successResponse = new LoginResponse("Success: User logged in", currentUser, new AuthToken("example token"));
        failureResponse = new LoginResponse("Login failed");

        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.checkLogin(validRequest, LoginServiceProxy.LOGIN_URL_PATH)).thenReturn(successResponse);
        Mockito.when(mockServerFacade.checkLogin(invalidRequest, LoginServiceProxy.LOGIN_URL_PATH)).thenReturn(failureResponse);

        feedServiceProxySpy = Mockito.spy(new LoginServiceProxy());
        Mockito.when(feedServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testLogin_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        LoginResponse response = feedServiceProxySpy.checkLogin(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testLogin_invalidRequest_returnsNoLogin() throws IOException, TweeterRemoteException {
        LoginResponse response = feedServiceProxySpy.checkLogin(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
