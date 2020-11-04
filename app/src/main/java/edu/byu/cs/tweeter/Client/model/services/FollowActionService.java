package edu.byu.cs.tweeter.Client.model.services;

import edu.byu.cs.tweeter.Client.net.ServerFacade;
import byu.edu.cs.tweeter.shared.request.FollowUserRequest;
import byu.edu.cs.tweeter.shared.request.UnfollowUserRequest;
import byu.edu.cs.tweeter.shared.response.FollowUserResponse;
import byu.edu.cs.tweeter.shared.response.UnfollowUserResponse;

public class FollowActionService {
    private static FollowActionService instance;
    private final ServerFacade serverFacade;

    public static FollowActionService getInstance() {
        if (instance == null) {
            instance = new FollowActionService();
        }
        return instance;
    }

    private FollowActionService() {
        serverFacade = new ServerFacade();
    }

    public FollowUserResponse followUser(FollowUserRequest request) {
        return serverFacade.followUser(request);
    }

    public UnfollowUserResponse unfollowUser(UnfollowUserRequest request) {
        return serverFacade.unfollowUser(request);
    }
}