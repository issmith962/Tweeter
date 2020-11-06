package byu.edu.cs.tweeter.shared.model.domain.service;


import byu.edu.cs.tweeter.shared.request.CheckUserFollowingRequest;
import byu.edu.cs.tweeter.shared.response.CheckUserFollowingResponse;

public interface CheckUserFollowingService {
    public CheckUserFollowingResponse isUserFollowing(CheckUserFollowingRequest request);
}
