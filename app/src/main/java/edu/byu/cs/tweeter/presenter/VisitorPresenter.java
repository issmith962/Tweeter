package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.services.CheckUserFollowingService;
import edu.byu.cs.tweeter.model.services.FollowActionService;
import edu.byu.cs.tweeter.net.request.CheckUserFollowingRequest;
import edu.byu.cs.tweeter.net.request.FollowUserRequest;
import edu.byu.cs.tweeter.net.request.UnfollowUserRequest;
import edu.byu.cs.tweeter.net.response.CheckUserFollowingResponse;
import edu.byu.cs.tweeter.net.response.FollowUserResponse;
import edu.byu.cs.tweeter.net.response.UnfollowUserResponse;

public class VisitorPresenter extends Presenter {
    private final View view;
    public interface View {}
    public VisitorPresenter(View view) {
        this.view = view;
    }


    public CheckUserFollowingResponse isUserFollowing(CheckUserFollowingRequest request) {
        return CheckUserFollowingService.getInstance().isUserFollowing(request);
    }
    public FollowUserResponse followUser(FollowUserRequest request) {
        return FollowActionService.getInstance().followUser(request);
    }
    public UnfollowUserResponse unfollowUser(UnfollowUserRequest request) {
        return FollowActionService.getInstance().unfollowUser(request);
    }
}
