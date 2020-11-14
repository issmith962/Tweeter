package edu.byu.cs.tweeter.Client.model.services;

import java.io.IOException;
import java.net.URL;

import byu.edu.cs.tweeter.shared.model.domain.service.CheckUserFollowingService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.CheckUserFollowingRequest;
import byu.edu.cs.tweeter.shared.response.CheckUserFollowingResponse;

public class CheckUserFollowingServiceProxy extends Service implements CheckUserFollowingService {
    public static final String URL_PATH = "/checkfollows";
    public CheckUserFollowingResponse isUserFollowing(CheckUserFollowingRequest request) throws IOException, TweeterRemoteException {
        if ((request.getFollowerAlias() == null) || (request.getFolloweeAlias() == null)) {
            return new CheckUserFollowingResponse("Follower or followee missing..");
        }
        String param_alias1 = "/" + request.getFollowerAlias();
        String param_alias2 = "/" + request.getFolloweeAlias();
        String newUrlPath = param_alias1 + URL_PATH + param_alias2;
        return getServerFacade().isUserFollowing(request, newUrlPath);
    }
}
