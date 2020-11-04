package edu.byu.cs.tweeter.Client.model.services;

import edu.byu.cs.tweeter.Client.net.ServerFacade;
import edu.byu.cs.tweeter.Shared.request.CheckUserFollowingRequest;
import edu.byu.cs.tweeter.Shared.response.CheckUserFollowingResponse;

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
