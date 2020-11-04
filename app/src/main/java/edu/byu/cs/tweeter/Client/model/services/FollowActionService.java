package edu.byu.cs.tweeter.Client.model.services;

import edu.byu.cs.tweeter.Client.net.ServerFacade;
import edu.byu.cs.tweeter.Shared.request.FollowUserRequest;
import edu.byu.cs.tweeter.Shared.request.UnfollowUserRequest;
import edu.byu.cs.tweeter.Shared.response.FollowUserResponse;
import edu.byu.cs.tweeter.Shared.response.UnfollowUserResponse;

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