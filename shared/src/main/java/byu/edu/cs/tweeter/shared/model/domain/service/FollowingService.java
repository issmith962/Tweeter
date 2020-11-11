package byu.edu.cs.tweeter.shared.model.domain.service;


import java.io.IOException;

import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.FolloweeCountRequest;
import byu.edu.cs.tweeter.shared.request.FollowingRequest;
import byu.edu.cs.tweeter.shared.response.FolloweeCountResponse;
import byu.edu.cs.tweeter.shared.response.FollowingResponse;

/**
 * Contains the business logic for getting the users a user is following.
 */
public interface FollowingService {
    public FollowingResponse getFollowees(FollowingRequest request) throws AssertionError, IOException, TweeterRemoteException;
    public FolloweeCountResponse getFolloweeCount(FolloweeCountRequest request) throws IOException, TweeterRemoteException;
}
