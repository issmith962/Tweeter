package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.CheckUserFollowingRequest;
import edu.byu.cs.tweeter.net.response.CheckUserFollowingResponse;

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
