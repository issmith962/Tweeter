package byu.edu.cs.tweeter.shared.model.domain.service;


import java.io.IOException;

import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.RegisterRequest;
import byu.edu.cs.tweeter.shared.response.RegisterResponse;

public interface RegisterService {
    public RegisterResponse registerUser(RegisterRequest request) throws IOException, TweeterRemoteException;
}
