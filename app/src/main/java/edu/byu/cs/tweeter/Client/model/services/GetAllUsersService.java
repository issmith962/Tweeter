package edu.byu.cs.tweeter.Client.model.services;

import edu.byu.cs.tweeter.Client.net.ServerFacade;
import byu.edu.cs.tweeter.shared.request.GetAllUsersRequest;
import byu.edu.cs.tweeter.shared.response.GetAllUsersResponse;

public class GetAllUsersService {
    private static GetAllUsersService instance;
    private final ServerFacade serverFacade;

    public static GetAllUsersService getInstance() {
        if (instance == null) {
            instance = new GetAllUsersService();
        }
        return instance;
    }

    private GetAllUsersService() {
        serverFacade = new ServerFacade();
    }

    public GetAllUsersResponse getAllUsers(GetAllUsersRequest request) {
        return serverFacade.getAllUsers(request);
    }
}
