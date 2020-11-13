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
    public static final String LOGIN_URL_PATH = "/login";

    public LoginResponse checkLogin(LoginRequest request) throws IOException, TweeterRemoteException {
        return getServerFacade().checkLogin(request, LOGIN_URL_PATH);
    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public StartUpResponse startUp(StartUpRequest request) {
//        return getServerFacade().startUp(request);
//    }

}