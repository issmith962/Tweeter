package edu.byu.cs.tweeter.net.ServiceProxyTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.FolloweeCountRequest;
import byu.edu.cs.tweeter.shared.request.FollowingRequest;
import byu.edu.cs.tweeter.shared.response.FolloweeCountResponse;
import byu.edu.cs.tweeter.shared.response.FollowingResponse;
import edu.byu.cs.tweeter.Client.model.services.FollowingServiceProxy;
import edu.byu.cs.tweeter.Client.net.ServerFacade;
import byu.edu.cs.tweeter.shared.utils.ByteArrayUtils;

public class FollowingServiceProxyTest {

    private FollowingRequest validFollowingRequest;
    private FollowingRequest invalidFollowingRequest;

    private FollowingResponse successFollowingResponse;
    private FollowingResponse failureFollowingResponse;

    private FolloweeCountRequest validFolloweeCountRequest;
    private FolloweeCountRequest invalidFolloweeCountRequest;

    private FolloweeCountResponse successFolloweeCountResponse;
    private FolloweeCountResponse failureFolloweeCountResponse;

    private FollowingServiceProxy followingServiceProxySpy;

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
        failureFollowingResponse = new FollowingResponse("An exception occurred");

        validFolloweeCountRequest = new FolloweeCountRequest(currentUser);
        invalidFolloweeCountRequest = new FolloweeCountRequest(null);

        successFolloweeCountResponse = new FolloweeCountResponse(10);
        failureFolloweeCountResponse = new FolloweeCountResponse("No user identified..");

        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);

        Mockito.when(mockServerFacade.getFollowees(validFollowingRequest, FollowingServiceProxy.GETFOLLOWEES_URL_PATH)).thenReturn(successFollowingResponse);
        Mockito.when(mockServerFacade.getFollowees(invalidFollowingRequest, FollowingServiceProxy.GETFOLLOWEES_URL_PATH)).thenReturn(failureFollowingResponse);
        Mockito.when(mockServerFacade.getFolloweeCount(validFolloweeCountRequest, FollowingServiceProxy.FOLLOWEE_COUNT_URL_PATH +
                "/" + validFolloweeCountRequest.getUser().getAlias())).thenReturn(successFolloweeCountResponse);

        followingServiceProxySpy = Mockito.spy(new FollowingServiceProxy());
        Mockito.when(followingServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowingResponse response = followingServiceProxySpy.getFollowees(validFollowingRequest);
        Assertions.assertEquals(successFollowingResponse, response);
    }

    @Test
    public void testGetFollowees_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        FollowingResponse response = followingServiceProxySpy.getFollowees(validFollowingRequest);

        for (User user : response.getFollowees()) {
            Assertions.assertNotNull(user.getImageUrl());
            Assertions.assertNotNull(ByteArrayUtils.bytesFromUrl(user.getImageUrl()));
        }
    }

    @Test
    public void testGetFollowees_invalidRequest_returnsNoFollowees() throws IOException, TweeterRemoteException {
        FollowingResponse response = followingServiceProxySpy.getFollowees(invalidFollowingRequest);
        Assertions.assertEquals(failureFollowingResponse, response);
    }


    @Test
    public void testFolloweeCount_validRequest() throws IOException, TweeterRemoteException {
        FolloweeCountResponse response = followingServiceProxySpy.getFolloweeCount(validFolloweeCountRequest);
        Assertions.assertEquals(successFolloweeCountResponse, response);
    }

    @Test
    public void testFolloweeCount_invalidRequest() throws IOException, TweeterRemoteException {
        FolloweeCountResponse response = followingServiceProxySpy.getFolloweeCount(invalidFolloweeCountRequest);
        Assertions.assertEquals(failureFolloweeCountResponse, response);
    }







}

