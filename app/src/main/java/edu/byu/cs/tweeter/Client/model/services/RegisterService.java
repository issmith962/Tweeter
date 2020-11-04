package edu.byu.cs.tweeter.Client.model.services;

import edu.byu.cs.tweeter.Client.net.ServerFacade;
import edu.byu.cs.tweeter.Shared.request.RegisterRequest;
import edu.byu.cs.tweeter.Shared.response.RegisterResponse;

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
