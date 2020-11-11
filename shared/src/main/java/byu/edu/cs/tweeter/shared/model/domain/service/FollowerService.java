package byu.edu.cs.tweeter.shared.model.domain.service;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.FollowerCountRequest;
import byu.edu.cs.tweeter.shared.request.FollowersRequest;
import byu.edu.cs.tweeter.shared.response.FollowerCountResponse;
import byu.edu.cs.tweeter.shared.response.FollowersResponse;

/**
 * Contains the business logic for getting the users a user has as followers.
 */
public interface FollowerService {
    public FollowersResponse getFollowers(FollowersRequest request) throws IOException, TweeterRemoteException;
    public FollowerCountResponse getFollowerCount(FollowerCountRequest request) throws IOException, TweeterRemoteException;
}
