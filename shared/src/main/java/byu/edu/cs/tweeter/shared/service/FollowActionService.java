package byu.edu.cs.tweeter.shared.service;

import byu.edu.cs.tweeter.shared.request.FollowUserRequest;
import byu.edu.cs.tweeter.shared.request.UnfollowUserRequest;
import byu.edu.cs.tweeter.shared.response.FollowUserResponse;
import byu.edu.cs.tweeter.shared.response.UnfollowUserResponse;

public interface FollowActionService {
    public FollowUserResponse followUser(FollowUserRequest request);
    public UnfollowUserResponse unfollowUser(UnfollowUserRequest request);
}