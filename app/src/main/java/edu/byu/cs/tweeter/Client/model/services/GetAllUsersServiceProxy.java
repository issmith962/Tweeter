package edu.byu.cs.tweeter.Client.model.services;

import byu.edu.cs.tweeter.shared.service.GetAllUsersService;
import edu.byu.cs.tweeter.Client.net.ServerFacade;
import byu.edu.cs.tweeter.shared.request.GetAllUsersRequest;
import byu.edu.cs.tweeter.shared.response.GetAllUsersResponse;

public class GetAllUsersServiceProxy extends Service implements GetAllUsersService {
    public GetAllUsersResponse getAllUsers(GetAllUsersRequest request) {
        return getServerFacade().getAllUsers(request);
    }
}
