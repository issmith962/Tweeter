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
import byu.edu.cs.tweeter.shared.model.domain.service.FollowActionService;
import byu.edu.cs.tweeter.shared.model.domain.service.GetUserService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.FollowUserRequest;
import byu.edu.cs.tweeter.shared.request.GetUserRequest;
import byu.edu.cs.tweeter.shared.request.UnfollowUserRequest;
import byu.edu.cs.tweeter.shared.response.FollowUserResponse;
import byu.edu.cs.tweeter.shared.response.GetUserResponse;
import byu.edu.cs.tweeter.shared.response.UnfollowUserResponse;
import edu.byu.cs.tweeter.Client.presenter.VisitorPresenter;

public class VisitorPresenterTest {
    private GetUserRequest getUserRequest;
    private GetUserResponse getUserResponse;
    private GetUserService mockGetUserService;

    private FollowUserRequest followUserRequest;
    private FollowUserResponse followUserResponse;

    private UnfollowUserRequest unfollowUserRequest;
    private UnfollowUserResponse unfollowUserResponse;

    private FollowActionService mockFollowActionService;

    private VisitorPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        User follower = new User("FirstName1", "LastName1", null);
        User followee = new User("FirstName2", "LastName2", null);

        //follow
        followUserRequest = new FollowUserRequest(follower, followee, new AuthToken("example token"));
        followUserResponse = new FollowUserResponse(true);
        mockFollowActionService = Mockito.mock(FollowActionService.class);
        Mockito.when(mockFollowActionService.followUser(followUserRequest)).thenReturn(followUserResponse);


        //unfollow
        unfollowUserRequest = new UnfollowUserRequest(follower, followee, new AuthToken("example token"));
        unfollowUserResponse = new UnfollowUserResponse(true);
        Mockito.when(mockFollowActionService.unfollowUser(unfollowUserRequest)).thenReturn(unfollowUserResponse);


        //getuser
        getUserRequest = new GetUserRequest("example alias");
        getUserResponse = new GetUserResponse(currentUser);
        mockGetUserService = Mockito.mock(GetUserService.class);
        Mockito.when(mockGetUserService.getUser(getUserRequest)).thenReturn(getUserResponse);


        presenter = Mockito.spy(new VisitorPresenter(new VisitorPresenter.View() {}));

        Mockito.when(presenter.getFollowActionService()).thenReturn(mockFollowActionService);
        Mockito.when(presenter.getGetUserService()).thenReturn(mockGetUserService);
    }

    @Test
    public void testGetUser_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockGetUserService.getUser(getUserRequest)).thenReturn(getUserResponse);
        Assertions.assertEquals(getUserResponse, presenter.getUser(getUserRequest));
    }

    @Test
    public void testGetUser_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockGetUserService.getUser(getUserRequest)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> presenter.getUser(getUserRequest));
    }

    @Test
    public void testFollowUser_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockFollowActionService.followUser(followUserRequest)).thenReturn(followUserResponse);
        Assertions.assertEquals(followUserResponse, presenter.followUser(followUserRequest));
    }

    @Test
    public void testFollowUser_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockFollowActionService.followUser(followUserRequest)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> presenter.followUser(followUserRequest));
    }

    @Test
    public void testUnfollowUser_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockFollowActionService.unfollowUser(unfollowUserRequest)).thenReturn(unfollowUserResponse);
        Assertions.assertEquals(unfollowUserResponse, presenter.unfollowUser(unfollowUserRequest));
    }

    @Test
    public void testUnfollowUser_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockFollowActionService.unfollowUser(unfollowUserRequest)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> presenter.unfollowUser(unfollowUserRequest));
    }

}
