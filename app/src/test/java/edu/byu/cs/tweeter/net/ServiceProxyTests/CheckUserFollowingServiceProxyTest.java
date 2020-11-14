package edu.byu.cs.tweeter.net.ServiceProxyTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.CheckUserFollowingRequest;
import byu.edu.cs.tweeter.shared.response.CheckUserFollowingResponse;
import edu.byu.cs.tweeter.Client.model.services.CheckUserFollowingServiceProxy;
import edu.byu.cs.tweeter.Client.net.ServerFacade;

public class CheckUserFollowingServiceProxyTest {
    private CheckUserFollowingRequest validRequest;
    private CheckUserFollowingRequest invalidRequest1;
    private CheckUserFollowingRequest invalidRequest2;

    private CheckUserFollowingResponse successResponse;
    private CheckUserFollowingResponse failureResponse;

    private CheckUserFollowingServiceProxy checkUserFollowingServiceProxySpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User follower = new User("FirstName1", "LastName1", null);
        User followee = new User("FirstName2", "LastName2", null);

        validRequest = new CheckUserFollowingRequest(follower.getAlias(), followee.getAlias());
        invalidRequest1 = new CheckUserFollowingRequest(null, followee.getAlias());
        invalidRequest2 = new CheckUserFollowingRequest(follower.getAlias(), null);

        successResponse = new CheckUserFollowingResponse(true);
        failureResponse = new CheckUserFollowingResponse("Follower or followee missing..");

        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);

        Mockito.when(mockServerFacade.isUserFollowing(validRequest, "/" +
                validRequest.getFollowerAlias() +
                CheckUserFollowingServiceProxy.URL_PATH + "/" +
                validRequest.getFolloweeAlias())).thenReturn(successResponse);

        checkUserFollowingServiceProxySpy = Mockito.spy(new CheckUserFollowingServiceProxy());
        Mockito.when(checkUserFollowingServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testCheckUserFollowing_validRequest() throws IOException, TweeterRemoteException {
        CheckUserFollowingResponse response = checkUserFollowingServiceProxySpy.isUserFollowing(validRequest);
        Assertions.assertEquals(response, successResponse);
    }

    @Test
    public void testCheckUserFollowing_nullFollower() throws IOException, TweeterRemoteException {
        CheckUserFollowingResponse response = checkUserFollowingServiceProxySpy.isUserFollowing(invalidRequest1);
        Assertions.assertEquals(response, failureResponse);
    }

    @Test
    public void testCheckUserFollowing_nullFollowee() throws IOException, TweeterRemoteException {
        CheckUserFollowingResponse response = checkUserFollowingServiceProxySpy.isUserFollowing(invalidRequest2);
        Assertions.assertEquals(response, failureResponse);
    }










}
