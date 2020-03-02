package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.GetAllUsersRequest;
import edu.byu.cs.tweeter.net.response.GetAllUsersResponse;

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
