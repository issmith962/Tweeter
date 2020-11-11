package edu.byu.cs.tweeter.Client.presenter;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.Client.model.services.FollowerServiceProxy;
import edu.byu.cs.tweeter.Client.model.services.FollowingServiceProxy;
import edu.byu.cs.tweeter.Client.model.services.LogoutServiceProxy;
import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.Client.model.services.LoginServiceProxy;
import byu.edu.cs.tweeter.shared.request.FolloweeCountRequest;
import byu.edu.cs.tweeter.shared.request.FollowerCountRequest;
import byu.edu.cs.tweeter.shared.request.LogoutRequest;
import byu.edu.cs.tweeter.shared.response.FolloweeCountResponse;
import byu.edu.cs.tweeter.shared.response.FollowerCountResponse;
import byu.edu.cs.tweeter.shared.response.LogoutResponse;

/**
 * A common base class for all presenters in the application.
 */
public abstract class Presenter {
    private static User currentUser;
    private static AuthToken currentAuthToken;

    public FollowerCountResponse getFollowerCount(FollowerCountRequest request) throws IOException, TweeterRemoteException {
        return (new FollowerServiceProxy()).getFollowerCount(request);
    }
    public FolloweeCountResponse getFolloweeCount(FolloweeCountRequest request) throws IOException, TweeterRemoteException {
        return (new FollowingServiceProxy()).getFolloweeCount(request);
    }
    public LogoutResponse logout(LogoutRequest request) throws IOException, TweeterRemoteException {
        return (new LogoutServiceProxy()).logout(request);
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
}
