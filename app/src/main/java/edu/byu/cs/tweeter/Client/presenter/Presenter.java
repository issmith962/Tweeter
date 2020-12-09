package edu.byu.cs.tweeter.Client.presenter;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.model.domain.service.CheckUserFollowingService;
import byu.edu.cs.tweeter.shared.model.domain.service.FollowerService;
import byu.edu.cs.tweeter.shared.model.domain.service.FollowingService;
import byu.edu.cs.tweeter.shared.model.domain.service.LogoutService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.CheckUserFollowingRequest;
import byu.edu.cs.tweeter.shared.request.FolloweeCountRequest;
import byu.edu.cs.tweeter.shared.request.FollowerCountRequest;
import byu.edu.cs.tweeter.shared.request.LogoutRequest;
import byu.edu.cs.tweeter.shared.response.CheckUserFollowingResponse;
import byu.edu.cs.tweeter.shared.response.FolloweeCountResponse;
import byu.edu.cs.tweeter.shared.response.FollowerCountResponse;
import byu.edu.cs.tweeter.shared.response.LogoutResponse;
import edu.byu.cs.tweeter.Client.model.services.CheckUserFollowingServiceProxy;
import edu.byu.cs.tweeter.Client.model.services.FollowerServiceProxy;
import edu.byu.cs.tweeter.Client.model.services.FollowingServiceProxy;
import edu.byu.cs.tweeter.Client.model.services.LogoutServiceProxy;

/**
 * A common base class for all presenters in the application.
 */
public abstract class Presenter {
    private static User currentUser;
    private static AuthToken currentAuthToken;

    public FollowerCountResponse getFollowerCount(FollowerCountRequest request) throws IOException, TweeterRemoteException {
        return getFollowerService().getFollowerCount(request);
    }
    public FolloweeCountResponse getFolloweeCount(FolloweeCountRequest request) throws IOException, TweeterRemoteException {
        return getFollowingService().getFolloweeCount(request);
    }
    public LogoutResponse logout(LogoutRequest request) throws IOException, TweeterRemoteException {
        return getLogoutService().logout(request);
    }

    public CheckUserFollowingResponse isUserFollowing(CheckUserFollowingRequest request) throws IOException, TweeterRemoteException {
        int a = 5;
        return getCheckUserFollowingService().isUserFollowing(request);
    }

    static void setCurrentUser(User currentUser) {
        Presenter.currentUser = currentUser;
    }

    static void setCurrentAuthToken(AuthToken currentAuthToken) {
        Presenter.currentAuthToken = currentAuthToken;
    }

    protected static User getCurrentUser() {
        return currentUser;
    }

    protected static AuthToken getCurrentAuthToken() {
        return currentAuthToken;
    }

    public FollowerService getFollowerService() {
        return new FollowerServiceProxy();
    }

    public LogoutService getLogoutService() {
        return new LogoutServiceProxy();
    }

    public FollowingService getFollowingService() {
        return new FollowingServiceProxy();
    }

    public CheckUserFollowingService getCheckUserFollowingService() {
        return new CheckUserFollowingServiceProxy();
    }
}
