package edu.byu.cs.tweeter.Client.model.services;

import byu.edu.cs.tweeter.shared.service.RegisterService;
import edu.byu.cs.tweeter.Client.net.ServerFacade;
import byu.edu.cs.tweeter.shared.request.RegisterRequest;
import byu.edu.cs.tweeter.shared.response.RegisterResponse;

public class RegisterServiceProxy extends Service implements RegisterService {
    public RegisterResponse registerUser(RegisterRequest request) {
        RegisterResponse response = getServerFacade().registerUser(request);
        return response;
    }
}
