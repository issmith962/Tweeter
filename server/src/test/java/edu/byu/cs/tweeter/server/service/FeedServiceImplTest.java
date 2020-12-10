/*
package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import byu.edu.cs.tweeter.server.dao.AuthTokenDAO;
import byu.edu.cs.tweeter.server.dao.FeedDAO;
import byu.edu.cs.tweeter.server.dao.dummydatadao.FollowDAO;
import byu.edu.cs.tweeter.server.dao.dummydatadao.StatusDAO;
import byu.edu.cs.tweeter.server.service.FeedServiceImpl;
import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.Status;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.FeedRequest;
import byu.edu.cs.tweeter.shared.response.FeedResponse;

public class FeedServiceImplTest {
    private FeedRequest validRequest;
    private FeedRequest invalidRequest;

    private FeedResponse successResponse;
    private FeedResponse failureResponse;

    private FeedServiceImpl feedServiceImplSpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);
        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        Status user1Status = new Status(resultUser1, 1607590601, "status text");
        Status user2Status = new Status(resultUser2, 1607590601, "status text");
        Status user3Status = new Status(resultUser3, 1607590601, "status text");

        validRequest = new FeedRequest(currentUser, 10, null, new AuthToken("example token"));
        invalidRequest = new FeedRequest(null, 10, null, new AuthToken("example token"));

        successResponse = new FeedResponse(Arrays.asList(user1Status, user2Status, user3Status), false);
        failureResponse = new FeedResponse("Bad Request: No user given..");

        FeedDAO mockFeedDAO = Mockito.mock(FeedDAO.class);
        FollowDAO mockFollowDAO = Mockito.mock(FollowDAO.class);
        AuthTokenDAO mockAuthTokenDAO = Mockito.mock(AuthTokenDAO.class);

        Mockito.when(mockFeedDAO.getFeed(validRequest.getUser(), validRequest.getLimit(), "1245")).thenReturn(successResponse);
        Mockito.when(mockFollowDAO.getAllFollowees(validRequest.getUser())).thenReturn(Arrays.asList(resultUser1, resultUser2, resultUser3));
        Mockito.when(mockAuthTokenDAO.validateAuthToken(validRequest.getAuthToken(), validRequest.getUser().getAlias())).thenReturn(true);

        feedServiceImplSpy = Mockito.spy(new FeedServiceImpl());
        Mockito.when(feedServiceImplSpy.getFeedDAO()).thenReturn();
        Mockito.when(feedServiceImplSpy.getFollowDAO()).thenReturn(mockFollowDAO);
        Mockito.when(feedServiceImplSpy.getAuthTokenDAO()).thenReturn(mockAuthTokenDAO);
    }

    @Test
    public void testGetFeed_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FeedResponse response = feedServiceImplSpy.getFeed(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetFeed_invalidRequest_returnsNoFeed() throws IOException, TweeterRemoteException {
        FeedResponse response = feedServiceImplSpy.getFeed(invalidRequest);
        Assertions.assertEquals(failureResponse.getMessage(), response.getMessage());
    }

}
*/
