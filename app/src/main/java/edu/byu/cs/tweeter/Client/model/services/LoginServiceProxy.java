package edu.byu.cs.tweeter.Client.model.services;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.model.domain.service.LoginService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.FolloweeCountRequest;
import byu.edu.cs.tweeter.shared.request.FollowerCountRequest;
import byu.edu.cs.tweeter.shared.request.LoginRequest;
import byu.edu.cs.tweeter.shared.request.StartUpRequest;
import byu.edu.cs.tweeter.shared.response.FolloweeCountResponse;
import byu.edu.cs.tweeter.shared.response.FollowerCountResponse;
import byu.edu.cs.tweeter.shared.response.LoginResponse;
import byu.edu.cs.tweeter.shared.response.StartUpResponse;

/**
 * Contains the business logic for login and sign up.
 */
public class LoginServiceProxy extends Service implements LoginService {
    private static final String LOGIN_URL_PATH = "/login";
    private static final String FOLLOWER_COUNT_URL_PATH = "/followercount";
    private static final String FOLLOWEE_COUNT_URL_PATH = "/followeecount";
    public LoginResponse checkLogin(LoginRequest request) throws IOException, TweeterRemoteException {
        return getServerFacade().checkLogin(request, LOGIN_URL_PATH);
    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public StartUpResponse startUp(StartUpRequest request) {
//        return getServerFacade().startUp(request);
//    }
    public FollowerCountResponse getFollowerCount(FollowerCountRequest request) throws IOException, TweeterRemoteException {
        String param_alias = "/" + request.getUser().getAlias();
        String newUrlPath = FOLLOWER_COUNT_URL_PATH + param_alias;
        return getServerFacade().getFollowerCount(request, newUrlPath);
    }
    public FolloweeCountResponse getFolloweeCount(FolloweeCountRequest request) throws IOException, TweeterRemoteException {
        String param_alias = "/" + request.getUser().getAlias();
        String newUrlPath = FOLLOWEE_COUNT_URL_PATH + param_alias;
        return getServerFacade().getFolloweeCount(request, newUrlPath);
    }
}