package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.GetUserRequest;
import edu.byu.cs.tweeter.net.response.GetUserResponse;

public class GetUserService {
    private static GetUserService instance;
    private final ServerFacade mServerFacade;

    public static GetUserService getInstance() {
        if (instance == null) {
            instance = new GetUserService();
        }
        return instance;
    }

    private GetUserService() {
        mServerFacade = new ServerFacade();
    }

    public GetUserResponse getUser(GetUserRequest request) {
        return mServerFacade.getUser(request);
    }
}
