package edu.byu.cs.tweeter.net.PresenterTests;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.model.domain.service.FollowingService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.FollowingRequest;
import byu.edu.cs.tweeter.shared.response.FollowingResponse;
import edu.byu.cs.tweeter.Client.presenter.FollowingPresenter;

public class FollowingPresenterTest {
    // tests getFollowees()

    private FollowingRequest request;
    private FollowingResponse response;
    private FollowingService mockFollowingService;
    private FollowingPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        request = new FollowingRequest(currentUser, 3, null);
        response = new FollowingResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);

        mockFollowingService = Mockito.mock(FollowingService.class);
        Mockito.when(mockFollowingService.getFollowees(request)).thenReturn(response);

        presenter = Mockito.spy(new FollowingPresenter(new FollowingPresenter.View() {}));
        Mockito.when(presenter.getFollowingService()).thenReturn(mockFollowingService);
    }

    @Test
    public void testGetFollowees_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockFollowingService.getFollowees(request)).thenReturn(response);
        Assertions.assertEquals(response, presenter.getFollowing(request));
    }

    @Test
    public void testGetFollowees_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockFollowingService.getFollowees(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> presenter.getFollowing(request));
    }
}
