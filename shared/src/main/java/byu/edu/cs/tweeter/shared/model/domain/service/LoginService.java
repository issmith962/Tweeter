package byu.edu.cs.tweeter.shared.model.domain.service;


import java.io.IOException;

import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.LoginRequest;
import byu.edu.cs.tweeter.shared.response.LoginResponse;

/**
 * Contains the business logic for login and sign up.
 */
public interface LoginService {
    public LoginResponse checkLogin(LoginRequest request) throws IOException, TweeterRemoteException;
    //public StartUpResponse startUp(StartUpRequest request);

}