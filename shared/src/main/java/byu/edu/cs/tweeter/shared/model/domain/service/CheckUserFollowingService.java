package byu.edu.cs.tweeter.shared.model.domain.service;


import java.io.IOException;

import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.CheckUserFollowingRequest;
import byu.edu.cs.tweeter.shared.response.CheckUserFollowingResponse;

public interface CheckUserFollowingService {
    public CheckUserFollowingResponse isUserFollowing(CheckUserFollowingRequest request) throws IOException, TweeterRemoteException;
}
