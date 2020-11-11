package edu.byu.cs.tweeter.Client.model.services;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.model.domain.service.FollowerService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.FollowerCountRequest;
import byu.edu.cs.tweeter.shared.request.FollowersRequest;
import byu.edu.cs.tweeter.shared.response.FollowerCountResponse;
import byu.edu.cs.tweeter.shared.response.FollowersResponse;

/**
 * Contains the business logic for getting the users a user has as followers.
 */
public class FollowerServiceProxy extends Service implements FollowerService {
    private static final String GETFOLLOWERS_URL_PATH = "/getfollowers";
    private static final String FOLLOWER_COUNT_URL_PATH = "/followercount";
    public FollowersResponse getFollowers(FollowersRequest request) throws IOException, TweeterRemoteException {
        return getServerFacade().getFollowers(request, GETFOLLOWERS_URL_PATH);
    }
    public FollowerCountResponse getFollowerCount(FollowerCountRequest request) throws IOException, TweeterRemoteException {
        String param_alias = "/" + request.getUser().getAlias();
        String newUrlPath = FOLLOWER_COUNT_URL_PATH + param_alias;
        return getServerFacade().getFollowerCount(request, newUrlPath);
    }
}
