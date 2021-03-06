package byu.edu.cs.tweeter.shared.model.domain.service;


import byu.edu.cs.tweeter.shared.request.GetAllUsersRequest;
import byu.edu.cs.tweeter.shared.response.GetAllUsersResponse;

public interface GetAllUsersService {
    public GetAllUsersResponse getAllUsers(GetAllUsersRequest request);
}
