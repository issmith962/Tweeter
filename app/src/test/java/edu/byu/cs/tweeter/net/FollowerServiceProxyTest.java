package edu.byu.cs.tweeter.net;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.FollowersRequest;
import byu.edu.cs.tweeter.shared.response.FollowersResponse;
import edu.byu.cs.tweeter.Client.model.services.FollowerServiceProxy;
import edu.byu.cs.tweeter.Client.net.ServerFacade;
import edu.byu.cs.tweeter.Client.view.util.ByteArrayUtils;

public class FollowerServiceProxyTest {
    private FollowersRequest validRequest;
    private FollowersRequest invalidRequest;

    private FollowersResponse successResponse;
    private FollowersResponse failureResponse;

    private FollowerServiceProxy followingServiceProxySpy;

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
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getFollowers(validRequest, FollowerServiceProxy.GETFOLLOWERS_URL_PATH)).thenReturn(successResponse);

        failureResponse = new FollowersResponse("An exception occurred");
        Mockito.when(mockServerFacade.getFollowers(invalidRequest, FollowerServiceProxy.GETFOLLOWERS_URL_PATH)).thenReturn(failureResponse);

        followingServiceProxySpy = Mockito.spy(new FollowerServiceProxy());
        Mockito.when(followingServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testGetFollowers_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowersResponse response = followingServiceProxySpy.getFollowers(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetFollowers_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        FollowersResponse response = followingServiceProxySpy.getFollowers(validRequest);

        for (User user : response.getFollowers()) {
            Assertions.assertNotNull(user.getImageUrl());
            Assertions.assertNotNull(ByteArrayUtils.bytesFromUrl(user.getImageUrl()));
        }
    }

    @Test
    public void testGetFollowers_invalidRequest_returnsNoFollowers() throws IOException, TweeterRemoteException {
        FollowersResponse response = followingServiceProxySpy.getFollowers(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }


}
