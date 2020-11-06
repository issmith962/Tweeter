package byu.edu.cs.tweeter.shared.service;


import byu.edu.cs.tweeter.shared.request.RegisterRequest;
import byu.edu.cs.tweeter.shared.response.RegisterResponse;

public interface RegisterService {
    public RegisterResponse registerUser(RegisterRequest request);
}
