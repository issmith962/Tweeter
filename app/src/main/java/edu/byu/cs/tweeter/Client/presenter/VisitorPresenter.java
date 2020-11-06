package edu.byu.cs.tweeter.Client.presenter;

import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.User;
import edu.byu.cs.tweeter.Client.model.services.CheckUserFollowingServiceProxy;
import edu.byu.cs.tweeter.Client.model.services.FollowActionServiceProxy;
import edu.byu.cs.tweeter.Client.model.services.GetUserServiceProxy;
import byu.edu.cs.tweeter.shared.request.CheckUserFollowingRequest;
import byu.edu.cs.tweeter.shared.request.FollowUserRequest;
import byu.edu.cs.tweeter.shared.request.GetUserRequest;
import byu.edu.cs.tweeter.shared.request.UnfollowUserRequest;
import byu.edu.cs.tweeter.shared.response.CheckUserFollowingResponse;
import byu.edu.cs.tweeter.shared.response.FollowUserResponse;
import byu.edu.cs.tweeter.shared.response.GetUserResponse;
import byu.edu.cs.tweeter.shared.response.UnfollowUserResponse;

public class VisitorPresenter extends Presenter {
    private final View view;
    public interface View {}
    public VisitorPresenter(View view) {
        this.view = view;
    }

    public CheckUserFollowingResponse isUserFollowing(CheckUserFollowingRequest request) {
        return (new CheckUserFollowingServiceProxy()).isUserFollowing(request);
    }
    public FollowUserResponse followUser(FollowUserRequest request) {
        return (new FollowActionServiceProxy()).followUser(request);
    }
    public UnfollowUserResponse unfollowUser(UnfollowUserRequest request) {
        return (new FollowActionServiceProxy()).unfollowUser(request);
    }
    public GetUserResponse getUser(GetUserRequest request) {
        return (new GetUserServiceProxy()).getUser(request);
    }

    public void updateCurrentUser(User user) {
        setCurrentUser(user);
    }
    public User findCurrentUser() {
        return getCurrentUser();
    }

    public void updateCurrentAuthToken(AuthToken authToken) {
        setCurrentAuthToken(authToken);
    }

    public AuthToken findCurrentAuthToken() {
        return getCurrentAuthToken();
    }

}
