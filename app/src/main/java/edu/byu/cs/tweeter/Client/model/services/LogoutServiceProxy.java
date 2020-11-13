package edu.byu.cs.tweeter.Client.model.services;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.model.domain.service.LogoutService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.LogoutRequest;
import byu.edu.cs.tweeter.shared.response.LogoutResponse;

public class LogoutServiceProxy extends Service implements LogoutService {
    public static final String URL_PATH = "/logout";

    public LogoutResponse logout(LogoutRequest request) throws IOException, TweeterRemoteException {
        return getServerFacade().logout(request, URL_PATH);
    }

}
