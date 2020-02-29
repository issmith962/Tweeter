package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.services.CheckUserFollowingService;
import edu.byu.cs.tweeter.net.request.CheckUserFollowingRequest;
import edu.byu.cs.tweeter.net.response.CheckUserFollowingResponse;

public class VisitorPresenter extends Presenter {
    private final View view;
    public interface View {}
    public VisitorPresenter(View view) {
        this.view = view;
    }


    public CheckUserFollowingResponse isUserFollowing(CheckUserFollowingRequest request) {
        return CheckUserFollowingService.getInstance().isUserFollowing(request);
    }
//    public FollorUserResponse followUser(FollowUserRequest request) {
//        return FollowUserService.getInstance().followUser(request);
//    }
//    public UnfollorUserResponse followUser(UnfollowUserRequest request) {
//        return UnfollowUserService.getInstance().unfollowUser(request);
//    }
}
