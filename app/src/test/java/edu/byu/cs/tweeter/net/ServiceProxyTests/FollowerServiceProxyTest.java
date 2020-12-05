package edu.byu.cs.tweeter.net.ServiceProxyTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.FollowerCountRequest;
import byu.edu.cs.tweeter.shared.request.FollowersRequest;
import byu.edu.cs.tweeter.shared.response.FollowerCountResponse;
import byu.edu.cs.tweeter.shared.response.FollowersResponse;
import edu.byu.cs.tweeter.Client.model.services.FollowerServiceProxy;
import edu.byu.cs.tweeter.Client.net.ServerFacade;
import byu.edu.cs.tweeter.shared.ByteArrayUtils;

public class FollowerServiceProxyTest {
    private FollowersRequest validRequest;
    private FollowersRequest invalidRequest;

    private FollowersResponse successResponse;
    private FollowersResponse failureResponse;

    private FollowerCountRequest validFollowerCountRequest;
    private FollowerCountRequest invalidFollowerCountRequest;

    private FollowerCountResponse successFollowerCountResponse;
    private FollowerCountResponse failureFollowerCountResponse;

    private FollowerServiceProxy followerServiceProxySpy;

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
        failureResponse = new FollowersResponse("An exception occurred");

        validFollowerCountRequest = new FollowerCountRequest(currentUser);
        invalidFollowerCountRequest = new FollowerCountRequest(null);

        successFollowerCountResponse = new FollowerCountResponse(10);
        failureFollowerCountResponse = new FollowerCountResponse("No user identified..");

        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getFollowers(validRequest, FollowerServiceProxy.GETFOLLOWERS_URL_PATH)).thenReturn(successResponse);
        Mockito.when(mockServerFacade.getFollowers(invalidRequest, FollowerServiceProxy.GETFOLLOWERS_URL_PATH)).thenReturn(failureResponse);
        Mockito.when(mockServerFacade.getFollowerCount(validFollowerCountRequest, FollowerServiceProxy.FOLLOWER_COUNT_URL_PATH +
                "/" + validFollowerCountRequest.getUser().getAlias())).thenReturn(successFollowerCountResponse);

        followerServiceProxySpy = Mockito.spy(new FollowerServiceProxy());
        Mockito.when(followerServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testGetFollowers_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowersResponse response = followerServiceProxySpy.getFollowers(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetFollowers_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        FollowersResponse response = followerServiceProxySpy.getFollowers(validRequest);

        for (User user : response.getFollowers()) {
            Assertions.assertNotNull(user.getImageUrl());
            Assertions.assertNotNull(ByteArrayUtils.bytesFromUrl(user.getImageUrl()));
        }
    }

    @Test
    public void testGetFollowers_invalidRequest_returnsNoFollowers() throws IOException, TweeterRemoteException {
        FollowersResponse response = followerServiceProxySpy.getFollowers(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }

    @Test
    public void testFollowerCount_validRequest() throws IOException, TweeterRemoteException {
        FollowerCountResponse response = followerServiceProxySpy.getFollowerCount(validFollowerCountRequest);
        Assertions.assertEquals(successFollowerCountResponse, response);
    }

    @Test
    public void testFollowerCount_invalidRequest() throws IOException, TweeterRemoteException {
        FollowerCountResponse response = followerServiceProxySpy.getFollowerCount(invalidFollowerCountRequest);
        Assertions.assertEquals(failureFollowerCountResponse, response);
    }



}
