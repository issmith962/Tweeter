package edu.byu.cs.tweeter.Client.presenter;

import edu.byu.cs.tweeter.Client.model.services.CheckUserFollowingService;
import edu.byu.cs.tweeter.Client.model.services.FollowActionService;
import edu.byu.cs.tweeter.Client.model.services.GetUserService;
import edu.byu.cs.tweeter.Shared.request.CheckUserFollowingRequest;
import edu.byu.cs.tweeter.Shared.request.FollowUserRequest;
import edu.byu.cs.tweeter.Shared.request.GetUserRequest;
import edu.byu.cs.tweeter.Shared.request.UnfollowUserRequest;
import edu.byu.cs.tweeter.Shared.response.CheckUserFollowingResponse;
import edu.byu.cs.tweeter.Shared.response.FollowUserResponse;
import edu.byu.cs.tweeter.Shared.response.GetUserResponse;
import edu.byu.cs.tweeter.Shared.response.UnfollowUserResponse;

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
