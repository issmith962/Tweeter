package edu.byu.cs.tweeter.net.PresenterTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.model.domain.service.CheckUserFollowingService;
import byu.edu.cs.tweeter.shared.model.domain.service.FollowerService;
import byu.edu.cs.tweeter.shared.model.domain.service.FollowingService;
import byu.edu.cs.tweeter.shared.model.domain.service.LogoutService;
import byu.edu.cs.tweeter.shared.model.domain.service.PostStatusService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.CheckUserFollowingRequest;
import byu.edu.cs.tweeter.shared.request.FolloweeCountRequest;
import byu.edu.cs.tweeter.shared.request.FollowerCountRequest;
import byu.edu.cs.tweeter.shared.request.LogoutRequest;
import byu.edu.cs.tweeter.shared.request.PostStatusRequest;
import byu.edu.cs.tweeter.shared.response.CheckUserFollowingResponse;
import byu.edu.cs.tweeter.shared.response.FolloweeCountResponse;
import byu.edu.cs.tweeter.shared.response.FollowerCountResponse;
import byu.edu.cs.tweeter.shared.response.LogoutResponse;
import byu.edu.cs.tweeter.shared.response.PostStatusResponse;
import edu.byu.cs.tweeter.Client.presenter.MainPresenter;


// I'm also going to use this test class to test the abstract methods of Presenter
public class MainPresenterTest {

    private PostStatusRequest postStatusRequest;
    private PostStatusResponse postStatusResponse;
    private PostStatusService mockPostStatusService;

    private FollowerCountRequest followerCountRequest;
    private FollowerCountResponse followerCountResponse;
    private FollowerService mockFollowerService;

    private FolloweeCountRequest followeeCountRequest;
    private FolloweeCountResponse followeeCountResponse;
    private FollowingService mockFollowingService;

    private LogoutRequest logoutRequest;
    private LogoutResponse logoutResponse;
    private LogoutService mockLogoutService;

    private CheckUserFollowingRequest checkUserFollowingRequest;
    private CheckUserFollowingResponse checkUserFollowingResponse;
    private CheckUserFollowingService mockCheckUserFollowingService;

    private MainPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);
        User follower = new User("FirstName1", "LastName1", null);
        User followee = new User("FirstName2", "LastName2", null);

        //poststatus
        postStatusRequest = new PostStatusRequest(currentUser, "first status", 1607100054, new AuthToken("example token"));
        postStatusResponse = new PostStatusResponse(true);
        mockPostStatusService = Mockito.mock(PostStatusService.class);
        Mockito.when(mockPostStatusService.postStatus(postStatusRequest)).thenReturn(postStatusResponse);

        //followerCount
        followerCountRequest = new FollowerCountRequest(currentUser);
        followerCountResponse = new FollowerCountResponse(10);
        mockFollowerService = Mockito.mock(FollowerService.class);
        Mockito.when(mockFollowerService.getFollowerCount(followerCountRequest)).thenReturn(followerCountResponse);

        //followeeCount
        followeeCountRequest = new FolloweeCountRequest(currentUser);
        followeeCountResponse = new FolloweeCountResponse(10);
        mockFollowingService = Mockito.mock(FollowingService.class);
        Mockito.when(mockFollowingService.getFolloweeCount(followeeCountRequest)).thenReturn(followeeCountResponse);

        //logout
        logoutRequest = new LogoutRequest(new AuthToken("example token"));
        logoutResponse = new LogoutResponse(true);
        mockLogoutService = Mockito.mock(LogoutService.class);
        Mockito.when(mockLogoutService.logout(logoutRequest)).thenReturn(logoutResponse);

        //checkUserFollowing
        checkUserFollowingRequest = new CheckUserFollowingRequest(follower.getAlias(), followee.getAlias());
        checkUserFollowingResponse = new CheckUserFollowingResponse(true);
        mockCheckUserFollowingService = Mockito.mock(CheckUserFollowingService.class);
        Mockito.when(mockCheckUserFollowingService.isUserFollowing(checkUserFollowingRequest)).thenReturn(checkUserFollowingResponse);

        presenter = Mockito.spy(new MainPresenter(new MainPresenter.View() {}));
        Mockito.when(presenter.getPostStatusService()).thenReturn(mockPostStatusService);
        Mockito.when(presenter.getFollowerService()).thenReturn(mockFollowerService);
        Mockito.when(presenter.getLogoutService()).thenReturn(mockLogoutService);
        Mockito.when(presenter.getFollowingService()).thenReturn(mockFollowingService);
        Mockito.when(presenter.getCheckUserFollowingService()).thenReturn(mockCheckUserFollowingService);
    }

    @Test
    public void testPostStatus_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockPostStatusService.postStatus(postStatusRequest)).thenReturn(postStatusResponse);
        Assertions.assertEquals(postStatusResponse, presenter.postStatus(postStatusRequest));
    }

    @Test
    public void testPostStatus_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockPostStatusService.postStatus(postStatusRequest)).thenThrow(new IOException());
        Assertions.assertThrows(IOException.class, () -> presenter.postStatus(postStatusRequest));
    }

    @Test
    public void testFollowerCount_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockFollowerService.getFollowerCount(followerCountRequest)).thenReturn(followerCountResponse);
        Assertions.assertEquals(followerCountResponse, presenter.getFollowerCount(followerCountRequest));

    }

    @Test
    public void testFollowerCount_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockFollowerService.getFollowerCount(followerCountRequest)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> presenter.getFollowerCount(followerCountRequest));
    }

    @Test
    public void testFolloweeCountStatus_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockFollowingService.getFolloweeCount(followeeCountRequest)).thenReturn(followeeCountResponse);
        Assertions.assertEquals(followeeCountResponse, presenter.getFolloweeCount(followeeCountRequest));
    }

    @Test
    public void testFolloweeCountStatus_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockFollowingService.getFolloweeCount(followeeCountRequest)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> presenter.getFolloweeCount(followeeCountRequest));
    }

    @Test
    public void testLogout_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockLogoutService.logout(logoutRequest)).thenReturn(logoutResponse);
        Assertions.assertEquals(logoutResponse, presenter.logout(logoutRequest));
    }

    @Test
    public void testLogout_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockLogoutService.logout(logoutRequest)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> presenter.logout(logoutRequest));
    }

    @Test
    public void testCheckUserFollowing_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockCheckUserFollowingService.isUserFollowing(checkUserFollowingRequest)).thenReturn(checkUserFollowingResponse);
        Assertions.assertEquals(checkUserFollowingResponse, presenter.isUserFollowing(checkUserFollowingRequest));
    }

    @Test
    public void testCheckUserFollowing_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockCheckUserFollowingService.isUserFollowing(checkUserFollowingRequest)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> presenter.isUserFollowing(checkUserFollowingRequest));
    }












}
