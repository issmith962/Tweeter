package edu.byu.cs.tweeter.Client.model.services;

import byu.edu.cs.tweeter.shared.service.CheckUserFollowingService;
import byu.edu.cs.tweeter.shared.service.FollowingService;
import edu.byu.cs.tweeter.Client.net.ServerFacade;
import byu.edu.cs.tweeter.shared.request.FollowingRequest;
import byu.edu.cs.tweeter.shared.response.FollowingResponse;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowingServiceProxy extends Service implements FollowingService {
    public FollowingResponse getFollowees(FollowingRequest request) throws AssertionError {
        return getServerFacade().getFollowees(request);
    }


}
