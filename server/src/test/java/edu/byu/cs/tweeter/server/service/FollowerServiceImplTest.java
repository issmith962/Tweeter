/*
package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import byu.edu.cs.tweeter.server.dao.dummydatadao.FollowDAO;
import byu.edu.cs.tweeter.server.service.FollowerServiceImpl;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.FollowerCountRequest;
import byu.edu.cs.tweeter.shared.request.FollowersRequest;
import byu.edu.cs.tweeter.shared.response.FollowerCountResponse;
import byu.edu.cs.tweeter.shared.response.FollowersResponse;

public class FollowerServiceImplTest {
    private FollowersRequest validRequest;
    private FollowersRequest invalidRequest;

    private FollowersResponse successResponse;
    private FollowersResponse failureResponse;

    private FollowerCountRequest validFollowerCountRequest;
    private FollowerCountRequest invalidFollowerCountRequest;

    private FollowerCountResponse successFollowerCountResponse;
    private FollowerCountResponse failureFollowerCountResponse;

    private FollowerServiceImpl followerServiceImplSpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);
        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        validRequest = new FollowersRequest(currentUser, 3, null);
        invalidRequest = new FollowersRequest(null, 0, null);

        successResponse = new FollowersResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
        failureResponse = new FollowersResponse("Bad Request: no user given..");

        validFollowerCountRequest = new FollowerCountRequest(currentUser);
        invalidFollowerCountRequest = new FollowerCountRequest(null);

        successFollowerCountResponse = new FollowerCountResponse(10);
        failureFollowerCountResponse = new FollowerCountResponse("Bad Request: no user given..");

        FollowDAO mockFollowDAO = Mockito.mock(FollowDAO.class);
        Mockito.when(mockFollowDAO.getFollowers(validRequest)).thenReturn(successResponse);
        Mockito.when(mockFollowDAO.getFollowerCount(validFollowerCountRequest)).thenReturn(10);

        followerServiceImplSpy = Mockito.spy(new FollowerServiceImpl());
        Mockito.when(followerServiceImplSpy.getFollowDAO()).thenReturn(mockFollowDAO);
    }

    @Test
    public void testGetFollowers_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowersResponse response = followerServiceImplSpy.getFollowers(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetFollowers_invalidRequest_returnsNoFollowers() throws IOException, TweeterRemoteException {
        FollowersResponse response = followerServiceImplSpy.getFollowers(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }

    @Test
    public void testFollowerCount_validRequest() throws IOException, TweeterRemoteException {
        FollowerCountResponse response = followerServiceImplSpy.getFollowerCount(validFollowerCountRequest);
        Assertions.assertEquals(successFollowerCountResponse.getMessage(), response.getMessage());
    }

    @Test
    public void testFollowerCount_invalidRequest() throws IOException, TweeterRemoteException {
        FollowerCountResponse response = followerServiceImplSpy.getFollowerCount(invalidFollowerCountRequest);
        Assertions.assertEquals(failureFollowerCountResponse, response);
    }


}
*/
