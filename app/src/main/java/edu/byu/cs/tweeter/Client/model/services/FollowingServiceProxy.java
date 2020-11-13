package edu.byu.cs.tweeter.Client.model.services;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.model.domain.service.FollowingService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.FolloweeCountRequest;
import byu.edu.cs.tweeter.shared.request.FollowingRequest;
import byu.edu.cs.tweeter.shared.response.FolloweeCountResponse;
import byu.edu.cs.tweeter.shared.response.FollowingResponse;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowingServiceProxy extends Service implements FollowingService {
    public static final String GETFOLLOWEES_URL_PATH = "/getfollowees";
    public static final String FOLLOWEE_COUNT_URL_PATH = "/followeecount";

    public FollowingResponse getFollowees(FollowingRequest request) throws AssertionError, IOException, TweeterRemoteException {
        return getServerFacade().getFollowees(request, GETFOLLOWEES_URL_PATH);
    }
    public FolloweeCountResponse getFolloweeCount(FolloweeCountRequest request) throws IOException, TweeterRemoteException {
        String param_alias = "/" + request.getUser().getAlias();
        String newUrlPath = FOLLOWEE_COUNT_URL_PATH + param_alias;
        return getServerFacade().getFolloweeCount(request, newUrlPath);
    }


}
