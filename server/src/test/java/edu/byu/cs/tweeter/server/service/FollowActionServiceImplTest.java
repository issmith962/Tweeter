/*
package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import byu.edu.cs.tweeter.server.dao.AuthTokenDAO;
import byu.edu.cs.tweeter.server.dao.dummydatadao.FollowDAO;
import byu.edu.cs.tweeter.server.service.FollowActionServiceImpl;
import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.FollowUserRequest;
import byu.edu.cs.tweeter.shared.request.UnfollowUserRequest;
import byu.edu.cs.tweeter.shared.response.FollowUserResponse;
import byu.edu.cs.tweeter.shared.response.UnfollowUserResponse;

public class FollowActionServiceImplTest {
    private FollowUserRequest validFollowRequest;
    private UnfollowUserRequest validUnfollowRequest;
    private FollowUserRequest invalidFollowRequest1;
    private FollowUserRequest invalidFollowRequest2;
    private UnfollowUserRequest invalidUnfollowRequest1;
    private UnfollowUserRequest invalidUnfollowRequest2;

    private FollowUserResponse successFollowResponse;
    private UnfollowUserResponse successUnfollowResponse;
    private FollowUserResponse failureFollowResponse;
    private UnfollowUserResponse failureUnfollowResponse;

    private FollowActionServiceImpl followActionServiceImplSpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User follower = new User("FirstName1", "LastName1", null);
        User followee = new User("FirstName2", "LastName2", null);

        validFollowRequest = new FollowUserRequest(follower, followee, new AuthToken("example token"));
        invalidFollowRequest1 = new FollowUserRequest(null, followee, new AuthToken("example token"));
        invalidFollowRequest2 = new FollowUserRequest(follower, null, new AuthToken("example token"));

        validUnfollowRequest = new UnfollowUserRequest(follower, followee, new AuthToken("example token"));
        invalidUnfollowRequest1 = new UnfollowUserRequest(null, followee, new AuthToken("example token"));
        invalidUnfollowRequest2 = new UnfollowUserRequest(follower, null, new AuthToken("example token"));

        successFollowResponse = new FollowUserResponse(true);
        failureFollowResponse = new FollowUserResponse(false, "Bad Request: missing follower or followee alias..");

        successUnfollowResponse = new UnfollowUserResponse(true);
        failureUnfollowResponse = new UnfollowUserResponse(false, "Bad Request: missing follower or followee alias..");

        FollowDAO mockFollowDAO = Mockito.mock(FollowDAO.class);
        AuthTokenDAO mockAuthTokenDAO = Mockito.mock(AuthTokenDAO.class);

        Mockito.when(mockFollowDAO.followUser(validFollowRequest)).thenReturn(successFollowResponse);
        Mockito.when(mockFollowDAO.unfollowUser(validUnfollowRequest)).thenReturn(successUnfollowResponse);
        Mockito.when(mockAuthTokenDAO.validateAuthToken(validFollowRequest.getAuthToken(), validFollowRequest.getFollower().getAlias())).thenReturn(true);
        Mockito.when(mockAuthTokenDAO.validateAuthToken(validUnfollowRequest.getAuthToken(), validUnfollowRequest.getFollower().getAlias())).thenReturn(true);

        followActionServiceImplSpy = Mockito.spy(new FollowActionServiceImpl());
        Mockito.when(followActionServiceImplSpy.getFollowDAO()).thenReturn(mockFollowDAO);
        Mockito.when(followActionServiceImplSpy.getAuthTokenDAO()).thenReturn(mockAuthTokenDAO);

    }

    @Test
    public void testFollowUser_validRequest() throws IOException, TweeterRemoteException {
        FollowUserResponse response = followActionServiceImplSpy.followUser(validFollowRequest);
        Assertions.assertEquals(response, successFollowResponse);
    }

    @Test
    public void testUnfollowUser_validRequest() throws IOException, TweeterRemoteException {
        UnfollowUserResponse response = followActionServiceImplSpy.unfollowUser(validUnfollowRequest);
        Assertions.assertEquals(response, successUnfollowResponse);
    }

    @Test
    public void testFollowUser_invalidFollower() throws IOException, TweeterRemoteException {
        FollowUserResponse response = followActionServiceImplSpy.followUser(invalidFollowRequest1);
        Assertions.assertEquals(response, failureFollowResponse);
    }

    @Test
    public void testUnfollowUser_invalidFollower() throws IOException, TweeterRemoteException {
        UnfollowUserResponse response = followActionServiceImplSpy.unfollowUser(invalidUnfollowRequest1);
        Assertions.assertEquals(response, failureUnfollowResponse);
    }

    @Test
    public void testFollowUser_invalidFollowee() throws IOException, TweeterRemoteException {
        FollowUserResponse response = followActionServiceImplSpy.followUser(invalidFollowRequest2);
        Assertions.assertEquals(response, failureFollowResponse);
    }

    @Test
    public void testUnfollowUser_invalidFollowee() throws IOException, TweeterRemoteException {
        UnfollowUserResponse response = followActionServiceImplSpy.unfollowUser(invalidUnfollowRequest2);
        Assertions.assertEquals(response, failureUnfollowResponse);
    }



}
*/
