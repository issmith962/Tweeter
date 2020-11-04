package edu.byu.cs.tweeter.Client.model.services;

import edu.byu.cs.tweeter.Client.net.ServerFacade;
import byu.edu.cs.tweeter.shared.request.CheckUserFollowingRequest;
import byu.edu.cs.tweeter.shared.response.CheckUserFollowingResponse;

public class CheckUserFollowingService {
    private static CheckUserFollowingService instance;
    private final ServerFacade serverFacade;
    public static CheckUserFollowingService getInstance() {
        if (instance == null){
            instance = new CheckUserFollowingService();
        }
        return instance;
    }
    private CheckUserFollowingService() {
        serverFacade = new ServerFacade();
    }
    public CheckUserFollowingResponse isUserFollowing(CheckUserFollowingRequest request) {
        return serverFacade.isUserFollowing(request);
    }
}
