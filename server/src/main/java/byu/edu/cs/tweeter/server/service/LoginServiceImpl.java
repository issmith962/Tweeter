package byu.edu.cs.tweeter.server.service;

import byu.edu.cs.tweeter.shared.model.domain.service.LoginService;
import byu.edu.cs.tweeter.shared.request.FolloweeCountRequest;
import byu.edu.cs.tweeter.shared.request.FollowerCountRequest;
import byu.edu.cs.tweeter.shared.request.LoginRequest;
import byu.edu.cs.tweeter.shared.request.StartUpRequest;
import byu.edu.cs.tweeter.shared.response.FolloweeCountResponse;
import byu.edu.cs.tweeter.shared.response.FollowerCountResponse;
import byu.edu.cs.tweeter.shared.response.LoginResponse;
import byu.edu.cs.tweeter.shared.response.StartUpResponse;

public class LoginServiceImpl implements LoginService {
    @Override
    public LoginResponse checkLogin(LoginRequest request) {
        return null;
    }

//    @Override
//    public StartUpResponse startUp(StartUpRequest request) {
//        return null;
//    }

    @Override
    public FollowerCountResponse getFollowerCount(FollowerCountRequest request) {
        return null;
    }

    @Override
    public FolloweeCountResponse getFolloweeCount(FolloweeCountRequest request) {
        return null;
    }
}
