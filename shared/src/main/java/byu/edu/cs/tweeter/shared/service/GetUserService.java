package byu.edu.cs.tweeter.shared.service;

import byu.edu.cs.tweeter.shared.request.GetUserRequest;
import byu.edu.cs.tweeter.shared.response.GetUserResponse;

public interface GetUserService {
    public GetUserResponse getUser(GetUserRequest request);
}
