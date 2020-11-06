package edu.byu.cs.tweeter.Client.presenter;

import edu.byu.cs.tweeter.Client.model.services.LogoutServiceProxy;
import byu.edu.cs.tweeter.shared.domain.AuthToken;
import byu.edu.cs.tweeter.shared.domain.User;
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

    public FollowerCountResponse getFollowerCount(FollowerCountRequest request) {
        return (new LoginServiceProxy()).getFollowerCount(request);
    }
    public FolloweeCountResponse getFolloweeCount(FolloweeCountRequest request) {
        return (new LoginServiceProxy()).getFolloweeCount(request);
    }
    public LogoutResponse logout(LogoutRequest request) {
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
