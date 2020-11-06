package edu.byu.cs.tweeter.Client.model.services;

import byu.edu.cs.tweeter.shared.service.CheckUserFollowingService;
import edu.byu.cs.tweeter.Client.net.ServerFacade;
import byu.edu.cs.tweeter.shared.request.CheckUserFollowingRequest;
import byu.edu.cs.tweeter.shared.response.CheckUserFollowingResponse;

public class CheckUserFollowingServiceProxy extends Service implements CheckUserFollowingService {
    public CheckUserFollowingResponse isUserFollowing(CheckUserFollowingRequest request) {
        return getServerFacade().isUserFollowing(request);
    }
}
