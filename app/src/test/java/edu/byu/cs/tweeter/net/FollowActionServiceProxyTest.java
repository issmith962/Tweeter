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
import byu.edu.cs.tweeter.shared.request.FollowUserRequest;
import byu.edu.cs.tweeter.shared.request.UnfollowUserRequest;
import byu.edu.cs.tweeter.shared.response.FollowUserResponse;
import byu.edu.cs.tweeter.shared.response.UnfollowUserResponse;
import edu.byu.cs.tweeter.Client.model.services.FollowActionServiceProxy;
import edu.byu.cs.tweeter.Client.net.ServerFacade;

public class FollowActionServiceProxyTest {
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

    private FollowActionServiceProxy followActionServiceProxySpy;

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
        failureFollowResponse = new FollowUserResponse(false, "Potential follower or followee missing..");

        successUnfollowResponse = new UnfollowUserResponse(true);
        failureUnfollowResponse = new UnfollowUserResponse(false, "Follower or followee missing..");

        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);

        Mockito.when(mockServerFacade.followUser(validFollowRequest, "/" +
                validFollowRequest.getFollower().getAlias() +
                FollowActionServiceProxy.FOLLOW_URL_PATH + "/" +
                validFollowRequest.getFollowee().getAlias())).thenReturn(successFollowResponse);
        Mockito.when(mockServerFacade.unfollowUser(validUnfollowRequest, "/" +
                validUnfollowRequest.getFollower().getAlias() +
                FollowActionServiceProxy.UNFOLLOW_URL_PATH + "/"
                + validUnfollowRequest.getFollowee().getAlias())).thenReturn(successUnfollowResponse);

        followActionServiceProxySpy = Mockito.spy(new FollowActionServiceProxy());
        Mockito.when(followActionServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testFollowUser_validRequest() throws IOException, TweeterRemoteException {
        FollowUserResponse response = followActionServiceProxySpy.followUser(validFollowRequest);
        Assertions.assertEquals(response, successFollowResponse);
    }

    @Test
    public void testUnfollowUser_validRequest() throws IOException, TweeterRemoteException {
        UnfollowUserResponse response = followActionServiceProxySpy.unfollowUser(validUnfollowRequest);
        Assertions.assertEquals(response, successUnfollowResponse);
    }

    @Test
    public void testFollowUser_invalidFollower() throws IOException, TweeterRemoteException {
        FollowUserResponse response = followActionServiceProxySpy.followUser(invalidFollowRequest1);
        Assertions.assertEquals(response, failureFollowResponse);
    }

    @Test
    public void testUnfollowUser_invalidFollower() throws IOException, TweeterRemoteException {
        UnfollowUserResponse response = followActionServiceProxySpy.unfollowUser(invalidUnfollowRequest1);
        Assertions.assertEquals(response, failureUnfollowResponse);
    }

    @Test
    public void testFollowUser_invalidFollowee() throws IOException, TweeterRemoteException {
        FollowUserResponse response = followActionServiceProxySpy.followUser(invalidFollowRequest2);
        Assertions.assertEquals(response, failureFollowResponse);
    }

    @Test
    public void testUnfollowUser_invalidFollowee() throws IOException, TweeterRemoteException {
        UnfollowUserResponse response = followActionServiceProxySpy.unfollowUser(invalidUnfollowRequest2);
        Assertions.assertEquals(response, failureUnfollowResponse);
    }


}
