package edu.byu.cs.tweeter.Client.model.services;

import byu.edu.cs.tweeter.shared.service.FollowerService;
import edu.byu.cs.tweeter.Client.net.ServerFacade;
import byu.edu.cs.tweeter.shared.request.FollowersRequest;
import byu.edu.cs.tweeter.shared.response.FollowersResponse;

/**
 * Contains the business logic for getting the users a user has as followers.
 */
public class FollowerServiceProxy extends Service implements FollowerService {
    public FollowersResponse getFollowers(FollowersRequest request) {
        return getServerFacade().getFollowers(request);
    }
}
