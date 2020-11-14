package edu.byu.cs.tweeter.net.PresenterTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.model.domain.service.FollowerService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.FollowersRequest;
import byu.edu.cs.tweeter.shared.response.FollowersResponse;
import edu.byu.cs.tweeter.Client.presenter.FollowersPresenter;

public class FollowersPresenterTest {
    // tests getFollowers()

    private FollowersRequest request;
    private FollowersResponse response;
    private FollowerService mockFollowersService;
    private FollowersPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        request = new FollowersRequest(currentUser, 3, null);
        response = new FollowersResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);

        mockFollowersService = Mockito.mock(FollowerService.class);
        Mockito.when(mockFollowersService.getFollowers(request)).thenReturn(response);

        presenter = Mockito.spy(new FollowersPresenter(new FollowersPresenter.View() {}));
        Mockito.when(presenter.getFollowerService()).thenReturn(mockFollowersService);
    }

    @Test
    public void testGetFollowers_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockFollowersService.getFollowers(request)).thenReturn(response);
        Assertions.assertEquals(response, presenter.getFollowers(request));
    }

    @Test
    public void testGetFollowers_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockFollowersService.getFollowers(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> presenter.getFollowers(request));
    }
    
}
