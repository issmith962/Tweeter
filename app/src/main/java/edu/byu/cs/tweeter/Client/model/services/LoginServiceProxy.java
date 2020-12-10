package edu.byu.cs.tweeter.Client.model.services;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.model.domain.service.LoginService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.LoginRequest;
import byu.edu.cs.tweeter.shared.response.LoginResponse;

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