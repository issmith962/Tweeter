/*
package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import byu.edu.cs.tweeter.server.dao.dummydatadao.FollowDAO;
import byu.edu.cs.tweeter.server.service.FollowingServiceImpl;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.request.FolloweeCountRequest;
import byu.edu.cs.tweeter.shared.request.FollowingRequest;
import byu.edu.cs.tweeter.shared.response.FolloweeCountResponse;
import byu.edu.cs.tweeter.shared.response.FollowingResponse;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;


public class FollowingServiceImplTest {
    private FollowingRequest validFollowingRequest;
    private FollowingRequest invalidFollowingRequest;

    private FollowingResponse successFollowingResponse;
    private FollowingResponse failureFollowingResponse;

    private FolloweeCountRequest validFolloweeCountRequest;
    private FolloweeCountRequest invalidFolloweeCountRequest;

    private FolloweeCountResponse successFolloweeCountResponse;
    private FolloweeCountResponse failureFolloweeCountResponse;

    private FollowingServiceImpl followingServiceImplSpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);
        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        validFollowingRequest = new FollowingRequest(currentUser, 3, null);
        invalidFollowingRequest = new FollowingRequest(null, 0, null);

        successFollowingResponse = new FollowingResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
        failureFollowingResponse = new FollowingResponse("Bad Request: no user given..");

        validFolloweeCountRequest = new FolloweeCountRequest(currentUser);
        invalidFolloweeCountRequest = new FolloweeCountRequest(null);

        successFolloweeCountResponse = new FolloweeCountResponse(10);
        failureFolloweeCountResponse = new FolloweeCountResponse("Bad Request: no user given..");

        FollowDAO mockFollowDAO = Mockito.mock(FollowDAO.class);

        Mockito.when(mockFollowDAO.getFollowees(validFollowingRequest)).thenReturn(successFollowingResponse);
        Mockito.when(mockFollowDAO.getFolloweeCount(validFolloweeCountRequest)).thenReturn(10);

        followingServiceImplSpy = Mockito.spy(new FollowingServiceImpl());
        Mockito.when(followingServiceImplSpy.getFollowDAO()).thenReturn(mockFollowDAO);
    }

    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowingResponse response = followingServiceImplSpy.getFollowees(validFollowingRequest);
        Assertions.assertEquals(successFollowingResponse, response);
    }

    @Test
    public void testGetFollowees_invalidRequest_returnsNoFollowees() throws IOException, TweeterRemoteException {
        FollowingResponse response = followingServiceImplSpy.getFollowees(invalidFollowingRequest);
        Assertions.assertEquals(failureFollowingResponse, response);
    }


    @Test
    public void testFolloweeCount_validRequest() throws IOException, TweeterRemoteException {
        FolloweeCountResponse response = followingServiceImplSpy.getFolloweeCount(validFolloweeCountRequest);
        Assertions.assertEquals(successFolloweeCountResponse.getMessage(), response.getMessage());
    }

    @Test
    public void testFolloweeCount_invalidRequest() throws IOException, TweeterRemoteException {
        FolloweeCountResponse response = followingServiceImplSpy.getFolloweeCount(invalidFolloweeCountRequest);
        Assertions.assertEquals(failureFolloweeCountResponse, response);
    }



}
*/
