package edu.byu.cs.tweeter.Client.presenter;

import edu.byu.cs.tweeter.Client.model.services.LogoutService;
import byu.edu.cs.tweeter.shared.domain.AuthToken;
import byu.edu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.Client.model.services.LoginService;
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
