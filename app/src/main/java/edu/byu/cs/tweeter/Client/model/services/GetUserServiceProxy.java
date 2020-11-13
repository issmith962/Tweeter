package edu.byu.cs.tweeter.Client.model.services;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.model.domain.service.GetUserService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.GetUserRequest;
import byu.edu.cs.tweeter.shared.response.GetUserResponse;

public class GetUserServiceProxy extends Service implements GetUserService {
    public static final String URL_PATH = "/user";
    public GetUserResponse getUser(GetUserRequest request) throws IOException, TweeterRemoteException {
        String param_alias = "/" + request.getAlias();
        String newUrlPath = URL_PATH + param_alias;
        return getServerFacade().getUser(request, newUrlPath);
    }
}
