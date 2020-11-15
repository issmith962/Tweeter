package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import byu.edu.cs.tweeter.server.dao.AuthTokenDAO;
import byu.edu.cs.tweeter.server.dao.StatusDAO;
import byu.edu.cs.tweeter.server.service.PostStatusServiceImpl;
import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.PostStatusRequest;
import byu.edu.cs.tweeter.shared.response.PostStatusResponse;

public class PostStatusServiceImplTest {
    private PostStatusRequest validRequest;
    private PostStatusRequest invalidRequest;

    private PostStatusResponse successResponse;
    private PostStatusResponse failureResponse;

    private PostStatusServiceImpl postStatusServiceImplSpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        validRequest = new PostStatusRequest(currentUser, "first status", "01/01/2001", new AuthToken("example token"));
        invalidRequest = new PostStatusRequest(null, "first status", "01/01/2001", new AuthToken("example token"));

        successResponse = new PostStatusResponse(true);
        failureResponse = new PostStatusResponse(false, "Bad Request: no user given..");

        StatusDAO mockStatusDAO = Mockito.mock(StatusDAO.class);
        AuthTokenDAO mockAuthTokenDAO = Mockito.mock(AuthTokenDAO.class);

        Mockito.when(mockStatusDAO.postStatus(validRequest)).thenReturn(successResponse);
        Mockito.when(mockAuthTokenDAO.validateAuthToken(validRequest.getAuthToken(), validRequest.getUser().getAlias())).thenReturn(true);

        postStatusServiceImplSpy = Mockito.spy(new PostStatusServiceImpl());
        Mockito.when(postStatusServiceImplSpy.getStatusDAO()).thenReturn(mockStatusDAO);
        Mockito.when(postStatusServiceImplSpy.getAuthTokenDAO()).thenReturn(mockAuthTokenDAO);
    }

    @Test
    public void testPostStatus_validRequest() throws IOException, TweeterRemoteException {
        PostStatusResponse response = postStatusServiceImplSpy.postStatus(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetPostStatus_invalidRequest() throws IOException, TweeterRemoteException {
        PostStatusResponse response = postStatusServiceImplSpy.postStatus(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
