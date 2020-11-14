package edu.byu.cs.tweeter.net.PresenterTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.Status;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.model.domain.service.FeedService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.FeedRequest;
import byu.edu.cs.tweeter.shared.response.FeedResponse;
import edu.byu.cs.tweeter.Client.presenter.FeedPresenter;

public class FeedPresenterTest {
    private FeedRequest request;
    private FeedResponse response;
    private FeedService mockFeedService;
    private FeedPresenter presenter;

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

        request = new FeedRequest(currentUser, 10, null, new AuthToken("example token"));
        response = new FeedResponse(Arrays.asList(user1Status, user2Status, user3Status), false);

        mockFeedService = Mockito.mock(FeedService.class);
        Mockito.when(mockFeedService.getFeed(request)).thenReturn(response);

        presenter = Mockito.spy(new FeedPresenter(new FeedPresenter.View() {}));
        Mockito.when(presenter.getFeedService()).thenReturn(mockFeedService);
    }

    @Test
    public void testGetFeed_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockFeedService.getFeed(request)).thenReturn(response);
        Assertions.assertEquals(response, presenter.getFeed(request));
    }

    @Test
    public void testGetFeed_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockFeedService.getFeed(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> presenter.getFeed(request));
    }
    
}
