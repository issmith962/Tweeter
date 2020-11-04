package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.services.LoginService;
import edu.byu.cs.tweeter.model.services.LogoutService;
import edu.byu.cs.tweeter.net.request.FolloweeCountRequest;
import edu.byu.cs.tweeter.net.request.FollowerCountRequest;
import edu.byu.cs.tweeter.net.request.LogoutRequest;
import edu.byu.cs.tweeter.net.response.FolloweeCountResponse;
import edu.byu.cs.tweeter.net.response.FollowerCountResponse;
import edu.byu.cs.tweeter.net.response.LogoutResponse;

/**
 * A common base class for all presenters in the application.
 */
public abstract class Presenter {

    /**
     * Returns the currently logged in user.
     *
     * @return the user.
     */
    public User getCurrentUser() {
        return LoginService.getInstance().getCurrentUser();
    }
    public AuthToken getCurrentAuthToken() {
        return LoginService.getInstance().getCurrentAuthToken();
    }
    public FollowerCountResponse getFollowerCount(FollowerCountRequest request) {
        return LoginService.getInstance().getFollowerCount(request);
    }
    public FolloweeCountResponse getFolloweeCount(FolloweeCountRequest request) {
        return LoginService.getInstance().getFolloweeCount(request);
    }
    public LogoutResponse logout(LogoutRequest request) {
        return LogoutService.getInstance().logout(request);
    }
}
