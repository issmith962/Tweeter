package edu.byu.cs.tweeter.Client.model.services;

import edu.byu.cs.tweeter.Client.net.ServerFacade;
import byu.edu.cs.tweeter.shared.request.RegisterRequest;
import byu.edu.cs.tweeter.shared.response.RegisterResponse;

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
