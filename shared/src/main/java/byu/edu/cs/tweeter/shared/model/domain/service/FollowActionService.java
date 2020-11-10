package byu.edu.cs.tweeter.shared.model.domain.service;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.FollowUserRequest;
import byu.edu.cs.tweeter.shared.request.UnfollowUserRequest;
import byu.edu.cs.tweeter.shared.response.FollowUserResponse;
import byu.edu.cs.tweeter.shared.response.UnfollowUserResponse;

public interface FollowActionService {
    public FollowUserResponse followUser(FollowUserRequest request) throws IOException, TweeterRemoteException;
    public UnfollowUserResponse unfollowUser(UnfollowUserRequest request) throws IOException, TweeterRemoteException;
}