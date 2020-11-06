package edu.byu.cs.tweeter.Client.model.services;

import byu.edu.cs.tweeter.shared.service.GetUserService;
import edu.byu.cs.tweeter.Client.net.ServerFacade;
import byu.edu.cs.tweeter.shared.request.GetUserRequest;
import byu.edu.cs.tweeter.shared.response.GetUserResponse;

public class GetUserServiceProxy extends Service implements GetUserService {
    public GetUserResponse getUser(GetUserRequest request) {
        return getServerFacade().getUser(request);
    }
}
