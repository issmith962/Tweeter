package edu.byu.cs.tweeter.Client.presenter;

import edu.byu.cs.tweeter.Client.model.services.CheckUserFollowingService;
import edu.byu.cs.tweeter.Client.model.services.FollowActionService;
import edu.byu.cs.tweeter.Client.model.services.GetUserService;
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
        return CheckUserFollowingService.getInstance().isUserFollowing(request);
    }
    public FollowUserResponse followUser(FollowUserRequest request) {
        return FollowActionService.getInstance().followUser(request);
    }
    public UnfollowUserResponse unfollowUser(UnfollowUserRequest request) {
        return FollowActionService.getInstance().unfollowUser(request);
    }
    public GetUserResponse getUser(GetUserRequest request) {
        return GetUserService.getInstance().getUser(request);
    }

}
