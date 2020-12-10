/*
package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import byu.edu.cs.tweeter.server.dao.UserDAO;
import byu.edu.cs.tweeter.server.service.RegisterServiceImpl;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.RegisterRequest;
import byu.edu.cs.tweeter.shared.response.RegisterResponse;

public class RegisterServiceImplTest {
    private RegisterRequest validRequest;
    private RegisterRequest invalidRequest;

    private RegisterResponse successResponse;
    private RegisterResponse failureResponse;

    private RegisterServiceImpl registerServiceImplSpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        validRequest = new RegisterRequest("first last", "firstlast", "1234");
        invalidRequest = new RegisterRequest(null, "firstlast", "1234");

        successResponse = new RegisterResponse(currentUser, "1234");
        failureResponse = new RegisterResponse("Bad Request: one or more registration fields missing");

        UserDAO mockUserDAO = Mockito.mock(UserDAO.class);
        Mockito.when(mockUserDAO.register(validRequest)).thenReturn(successResponse);

        registerServiceImplSpy = Mockito.spy(new RegisterServiceImpl());
        Mockito.when(registerServiceImplSpy.getUserDAO()).thenReturn(mockUserDAO);
    }

    @Test
    public void testRegister_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        RegisterResponse response = registerServiceImplSpy.registerUser(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testRegister_invalidRequest_returnsNoRegister() throws IOException, TweeterRemoteException {
        RegisterResponse response = registerServiceImplSpy.registerUser(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
*/
