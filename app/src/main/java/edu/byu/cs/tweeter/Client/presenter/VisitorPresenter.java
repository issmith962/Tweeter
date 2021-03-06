package edu.byu.cs.tweeter.Client.presenter;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.model.domain.service.FollowActionService;
import byu.edu.cs.tweeter.shared.model.domain.service.GetUserService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
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

    public FollowActionService getFollowActionService() {
        return new FollowActionServiceProxy();
    }

    public GetUserService getGetUserService() {
        return new GetUserServiceProxy();
    }

    public interface View {}
    public VisitorPresenter(View view) {
        this.view = view;
    }

    public FollowUserResponse followUser(FollowUserRequest request) throws IOException, TweeterRemoteException {
        return getFollowActionService().followUser(request);
    }
    public UnfollowUserResponse unfollowUser(UnfollowUserRequest request) throws IOException, TweeterRemoteException {
        return getFollowActionService().unfollowUser(request);
    }
    public GetUserResponse getUser(GetUserRequest request) throws IOException, TweeterRemoteException {
        return getGetUserService().getUser(request);
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
