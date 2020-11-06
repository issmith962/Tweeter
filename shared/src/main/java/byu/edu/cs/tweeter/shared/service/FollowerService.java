package byu.edu.cs.tweeter.shared.service;

import byu.edu.cs.tweeter.shared.request.FollowersRequest;
import byu.edu.cs.tweeter.shared.response.FollowersResponse;

/**
 * Contains the business logic for getting the users a user has as followers.
 */
public interface FollowerService {
    public FollowersResponse getFollowers(FollowersRequest request);
}
