/*
package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import byu.edu.cs.tweeter.server.dao.FollowDAO;
import byu.edu.cs.tweeter.server.service.CheckUserFollowingServiceImpl;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.CheckUserFollowingRequest;
import byu.edu.cs.tweeter.shared.response.CheckUserFollowingResponse;

public class CheckUserFollowingServiceImplTest {

    private CheckUserFollowingRequest validRequest;
    private CheckUserFollowingRequest invalidRequest1;
    private CheckUserFollowingRequest invalidRequest2;

    private CheckUserFollowingResponse successResponse;
    private CheckUserFollowingResponse failureResponse;

    private CheckUserFollowingServiceImpl checkUserFollowingServiceImplSpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User follower = new User("FirstName1", "LastName1", null);
        User followee = new User("FirstName2", "LastName2", null);

        validRequest = new CheckUserFollowingRequest(follower.getAlias(), followee.getAlias());
        invalidRequest1 = new CheckUserFollowingRequest(null, followee.getAlias());
        invalidRequest2 = new CheckUserFollowingRequest(follower.getAlias(), null);

        successResponse = new CheckUserFollowingResponse(true);
        failureResponse = new CheckUserFollowingResponse("Bad Request: Missing follower or followee alias..");

        FollowDAO mockFollowDAO = Mockito.mock(FollowDAO.class);

        Mockito.when(mockFollowDAO.checkFollow(validRequest.getFolloweeAlias(), validRequest.getFollowerAlias())).thenReturn(true);

        checkUserFollowingServiceImplSpy = Mockito.spy(new CheckUserFollowingServiceImpl());
        Mockito.when(checkUserFollowingServiceImplSpy.getFollowDAO()).thenReturn(mockFollowDAO);
    }

    @Test
    public void testCheckUserFollowing_validRequest() throws IOException, TweeterRemoteException {
        CheckUserFollowingResponse response = checkUserFollowingServiceImplSpy.isUserFollowing(validRequest);
        Assertions.assertEquals(response, successResponse);
    }

    @Test
    public void testCheckUserFollowing_nullFollower() throws IOException, TweeterRemoteException {
        CheckUserFollowingResponse response = checkUserFollowingServiceImplSpy.isUserFollowing(invalidRequest1);
        Assertions.assertEquals(response, failureResponse);
    }

    @Test
    public void testCheckUserFollowing_nullFollowee() throws IOException, TweeterRemoteException {
        CheckUserFollowingResponse response = checkUserFollowingServiceImplSpy.isUserFollowing(invalidRequest2);
        Assertions.assertEquals(response, failureResponse);
    }

}
*/
