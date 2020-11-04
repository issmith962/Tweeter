package edu.byu.cs.tweeter.Client.model.services;

import edu.byu.cs.tweeter.Client.net.ServerFacade;
import edu.byu.cs.tweeter.Shared.request.GetUserRequest;
import edu.byu.cs.tweeter.Shared.response.GetUserResponse;

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
