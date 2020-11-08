package byu.edu.cs.tweeter.server.service;

import byu.edu.cs.tweeter.shared.model.domain.service.FollowActionService;
import byu.edu.cs.tweeter.shared.request.FollowUserRequest;
import byu.edu.cs.tweeter.shared.request.UnfollowUserRequest;
import byu.edu.cs.tweeter.shared.response.FollowUserResponse;
import byu.edu.cs.tweeter.shared.response.UnfollowUserResponse;

public class FollowActionServiceImpl implements FollowActionService
{
    @Override
    public FollowUserResponse followUser(FollowUserRequest request) {
        return null;
    }

    @Override
    public UnfollowUserResponse unfollowUser(UnfollowUserRequest request) {
        return null;
    }
}
