package edu.byu.cs.tweeter.Client.model.services;

import byu.edu.cs.tweeter.shared.service.FollowActionService;
import byu.edu.cs.tweeter.shared.request.FollowUserRequest;
import byu.edu.cs.tweeter.shared.request.UnfollowUserRequest;
import byu.edu.cs.tweeter.shared.response.FollowUserResponse;
import byu.edu.cs.tweeter.shared.response.UnfollowUserResponse;

public class FollowActionServiceProxy extends Service implements FollowActionService {
    public FollowUserResponse followUser(FollowUserRequest request) {
        return getServerFacade().followUser(request);
    }

    public UnfollowUserResponse unfollowUser(UnfollowUserRequest request) {
        return getServerFacade().unfollowUser(request);
    }
}