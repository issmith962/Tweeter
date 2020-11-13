package edu.byu.cs.tweeter.Client.model.services;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.model.domain.service.FollowActionService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.FollowUserRequest;
import byu.edu.cs.tweeter.shared.request.UnfollowUserRequest;
import byu.edu.cs.tweeter.shared.response.FollowUserResponse;
import byu.edu.cs.tweeter.shared.response.UnfollowUserResponse;

public class FollowActionServiceProxy extends Service implements FollowActionService {
    public static final String FOLLOW_URL_PATH = "/follow";
    public static final String UNFOLLOW_URL_PATH = "/unfollow";
    public FollowUserResponse followUser(FollowUserRequest request) throws IOException, TweeterRemoteException {
        String param_alias1 = "/" + request.getFollower().getAlias();
        String param_alias2 = "/" + request.getFollowee().getAlias();
        String newUrlPath = param_alias1 + FOLLOW_URL_PATH + param_alias2;
        return getServerFacade().followUser(request, newUrlPath);
    }

    public UnfollowUserResponse unfollowUser(UnfollowUserRequest request) throws IOException, TweeterRemoteException {
        String param_alias1 = "/" + request.getFollower().getAlias();
        String param_alias2 = "/" + request.getFollowee().getAlias();
        String newUrlPath = param_alias1 + UNFOLLOW_URL_PATH + param_alias2;
        return getServerFacade().unfollowUser(request, newUrlPath);
    }
}