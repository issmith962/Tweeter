package byu.edu.cs.tweeter.shared.service;


import byu.edu.cs.tweeter.shared.request.FollowingRequest;
import byu.edu.cs.tweeter.shared.response.FollowingResponse;

/**
 * Contains the business logic for getting the users a user is following.
 */
public interface FollowingService {
    public FollowingResponse getFollowees(FollowingRequest request) throws AssertionError;


}
