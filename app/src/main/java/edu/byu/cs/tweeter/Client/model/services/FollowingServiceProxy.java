package edu.byu.cs.tweeter.Client.model.services;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.model.domain.service.FollowingService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.FollowingRequest;
import byu.edu.cs.tweeter.shared.response.FollowingResponse;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowingServiceProxy extends Service implements FollowingService {
    private static final String URL_PATH = "/getfollowees";
    public FollowingResponse getFollowees(FollowingRequest request) throws AssertionError, IOException, TweeterRemoteException {
        return getServerFacade().getFollowees(request, URL_PATH);
    }


}
