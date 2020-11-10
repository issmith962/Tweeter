package edu.byu.cs.tweeter.Client.model.services;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.model.domain.service.FollowerService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.FollowersRequest;
import byu.edu.cs.tweeter.shared.response.FollowersResponse;

/**
 * Contains the business logic for getting the users a user has as followers.
 */
public class FollowerServiceProxy extends Service implements FollowerService {
    private static final String URL_PATH = "/getfollowers";
    public FollowersResponse getFollowers(FollowersRequest request) throws IOException, TweeterRemoteException {
        return getServerFacade().getFollowers(request, URL_PATH);
    }
}
