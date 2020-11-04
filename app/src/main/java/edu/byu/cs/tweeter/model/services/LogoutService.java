package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.LogoutRequest;
import edu.byu.cs.tweeter.net.response.LogoutResponse;

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
