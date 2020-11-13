package edu.byu.cs.tweeter.Client.model.services;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.model.domain.service.RegisterService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.RegisterRequest;
import byu.edu.cs.tweeter.shared.response.RegisterResponse;

public class RegisterServiceProxy extends Service implements RegisterService {
    public static final String URL_PATH = "/register";

    public RegisterResponse registerUser(RegisterRequest request) throws IOException, TweeterRemoteException {
        return getServerFacade().registerUser(request, URL_PATH);
    }
}
