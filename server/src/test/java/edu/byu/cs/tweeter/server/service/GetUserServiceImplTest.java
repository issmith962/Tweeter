/*
package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import byu.edu.cs.tweeter.server.dao.UserDAO;
import byu.edu.cs.tweeter.server.service.GetUserServiceImpl;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.GetUserRequest;
import byu.edu.cs.tweeter.shared.response.GetUserResponse;

public class GetUserServiceImplTest {
    private GetUserRequest validRequest;
    private GetUserRequest invalidRequest;

    private GetUserResponse successResponse;
    private GetUserResponse failureResponse;

    private GetUserServiceImpl getUserServiceImplSpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        validRequest = new GetUserRequest("example alias");
        invalidRequest = new GetUserRequest(null);

        successResponse = new GetUserResponse(currentUser);
        failureResponse = new GetUserResponse("Bad Request: no user requested..");

        UserDAO mockUserDAO = Mockito.mock(UserDAO.class);
        Mockito.when(mockUserDAO.findUserByAlias(validRequest.getAlias())).thenReturn(successResponse.getUser());

        getUserServiceImplSpy = Mockito.spy(new GetUserServiceImpl());
        Mockito.when(getUserServiceImplSpy.getUserDAO()).thenReturn(mockUserDAO);
    }

    @Test
    public void testGetUser_validRequest() throws IOException, TweeterRemoteException {
        GetUserResponse response = getUserServiceImplSpy.getUser(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetGetUser_invalidRequest() throws IOException, TweeterRemoteException {
        GetUserResponse response = getUserServiceImplSpy.getUser(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }

}
*/
