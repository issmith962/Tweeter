package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.FollowUserRequest;
import edu.byu.cs.tweeter.net.request.UnfollowUserRequest;
import edu.byu.cs.tweeter.net.response.FollowUserResponse;
import edu.byu.cs.tweeter.net.response.UnfollowUserResponse;

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