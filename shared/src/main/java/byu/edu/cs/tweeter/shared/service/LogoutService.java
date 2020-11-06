package byu.edu.cs.tweeter.shared.service;


import byu.edu.cs.tweeter.shared.request.LogoutRequest;
import byu.edu.cs.tweeter.shared.response.LogoutResponse;

public interface LogoutService {
    public LogoutResponse logout(LogoutRequest request);

}
