package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.services.LoginService;
import edu.byu.cs.tweeter.net.request.FolloweeCountRequest;
import edu.byu.cs.tweeter.net.request.FollowerCountRequest;
import edu.byu.cs.tweeter.net.response.FolloweeCountResponse;
import edu.byu.cs.tweeter.net.response.FollowerCountResponse;

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
    public FollowerCountResponse getFollowerCount(FollowerCountRequest request) {
        return LoginService.getInstance().getFollowerCount(request);
    }
    public FolloweeCountResponse getFolloweeCount(FolloweeCountRequest request) {
        return LoginService.getInstance().getFolloweeCount(request);
    }
}
