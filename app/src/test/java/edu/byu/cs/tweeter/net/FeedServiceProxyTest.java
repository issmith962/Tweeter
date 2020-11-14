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
import byu.edu.cs.tweeter.shared.request.FeedRequest;
import byu.edu.cs.tweeter.shared.response.FeedResponse;
import edu.byu.cs.tweeter.Client.model.services.FeedServiceProxy;
import edu.byu.cs.tweeter.Client.net.ServerFacade;

public class FeedServiceProxyTest {
    private FeedRequest validRequest;
    private FeedRequest invalidRequest;

    private FeedResponse successResponse;
    private FeedResponse failureResponse;

    private FeedServiceProxy feedServiceProxySpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);
        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        Status user1Status = new Status(resultUser1, "01/01/2001", "status text");
        Status user2Status = new Status(resultUser2, "02/02/2002", "status text");
        Status user3Status = new Status(resultUser3, "03/03/2003", "status text");

        validRequest = new FeedRequest(currentUser, 10, null, new AuthToken("example token"));
        invalidRequest = new FeedRequest(null, 10, null, null);

        successResponse = new FeedResponse(Arrays.asList(user1Status, user2Status, user3Status), false);
        failureResponse = new FeedResponse("An exception occurred");

        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getFeed(validRequest, FeedServiceProxy.URL_PATH)).thenReturn(successResponse);
        Mockito.when(mockServerFacade.getFeed(invalidRequest, FeedServiceProxy.URL_PATH)).thenReturn(failureResponse);

        feedServiceProxySpy = Mockito.spy(new FeedServiceProxy());
        Mockito.when(feedServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testGetFeed_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FeedResponse response = feedServiceProxySpy.getFeed(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetFeed_invalidRequest_returnsNoFeed() throws IOException, TweeterRemoteException {
        FeedResponse response = feedServiceProxySpy.getFeed(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }





}
