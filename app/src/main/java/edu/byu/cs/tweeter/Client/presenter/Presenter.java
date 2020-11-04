package edu.byu.cs.tweeter.Client.presenter;

import edu.byu.cs.tweeter.Client.model.services.LogoutService;
import edu.byu.cs.tweeter.Shared.domain.AuthToken;
import edu.byu.cs.tweeter.Shared.domain.User;
import edu.byu.cs.tweeter.Client.model.services.LoginService;
import edu.byu.cs.tweeter.Shared.request.FolloweeCountRequest;
import edu.byu.cs.tweeter.Shared.request.FollowerCountRequest;
import edu.byu.cs.tweeter.Shared.request.LogoutRequest;
import edu.byu.cs.tweeter.Shared.response.FolloweeCountResponse;
import edu.byu.cs.tweeter.Shared.response.FollowerCountResponse;
import edu.byu.cs.tweeter.Shared.response.LogoutResponse;

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
