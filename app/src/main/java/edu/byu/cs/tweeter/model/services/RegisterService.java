package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.RegisterRequest;
import edu.byu.cs.tweeter.net.response.RegisterResponse;

public class RegisterService {
    private static RegisterService instance;
    private final ServerFacade serverFacade;

    public static RegisterService getInstance() {
        if (instance == null) {
            instance = new RegisterService();
        }
        return instance;
    }

    private RegisterService() {
        serverFacade = new ServerFacade();
    }

    public RegisterResponse registerUser(RegisterRequest request) {
        RegisterResponse response = serverFacade.registerUser(request);
        return response;
    }
}
