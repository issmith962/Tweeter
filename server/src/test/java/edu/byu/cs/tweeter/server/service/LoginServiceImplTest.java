/*
package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Matchers.*;

import java.io.IOException;

import byu.edu.cs.tweeter.server.dao.AuthTokenDAO;
import byu.edu.cs.tweeter.server.dao.UserDAO;
import byu.edu.cs.tweeter.server.service.LoginServiceImpl;
import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.LoginRequest;
import byu.edu.cs.tweeter.shared.response.LoginResponse;

public class LoginServiceImplTest {
    private LoginRequest validRequest;
    private LoginRequest invalidRequest;

    private LoginResponse successResponse;
    private LoginResponse failureResponse;

    private LoginServiceImpl feedServiceImplSpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("Registered", "User", "alias", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        validRequest = new LoginRequest("alias", "password");
        invalidRequest = new LoginRequest("alias", null);

        successResponse = new LoginResponse("Success! Logged in..", currentUser, new AuthToken("example token"));
        failureResponse = new LoginResponse("Bad Request: no password given..");

        UserDAO mockUserDAO = Mockito.mock(UserDAO.class);
        AuthTokenDAO mockAuthTokenDAO = Mockito.mock(AuthTokenDAO.class);

        Mockito.when(mockUserDAO.validateLogin(validRequest.getAlias(), validRequest.getPassword())).thenReturn(true);

        feedServiceImplSpy = Mockito.spy(new LoginServiceImpl());
        Mockito.when(feedServiceImplSpy.getUserDAO()).thenReturn(mockUserDAO);
        Mockito.when(feedServiceImplSpy.getAuthTokenDAO()).thenReturn(mockAuthTokenDAO);

    }

    @Test
    public void testLogin_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        LoginResponse response = feedServiceImplSpy.checkLogin(validRequest);
        Assertions.assertEquals(successResponse.getUser(), response.getUser());
        Assertions.assertEquals(successResponse.getMessage(), response.getMessage());
        Assertions.assertEquals(successResponse.isSuccess(), response.isSuccess());
    }

    @Test
    public void testLogin_invalidRequest_returnsNoLogin() throws IOException, TweeterRemoteException {
        LoginResponse response = feedServiceImplSpy.checkLogin(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
*/
