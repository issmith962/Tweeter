package edu.byu.cs.tweeter.Client.model.services;

import edu.byu.cs.tweeter.Client.net.ServerFacade;
import byu.edu.cs.tweeter.shared.request.LogoutRequest;
import byu.edu.cs.tweeter.shared.response.LogoutResponse;

public class LogoutService {
    private static LogoutService instance;
    private final ServerFacade serverFacade;

    public static LogoutService getInstance() {
        if (instance == null) {
            instance = new LogoutService();
        }
        return instance;
    }

    private LogoutService() {
        serverFacade = new ServerFacade();
    }

    public LogoutResponse logout(LogoutRequest request) {
        return serverFacade.logout(request);
    }

}
