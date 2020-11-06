package edu.byu.cs.tweeter.Client.model.services;

import byu.edu.cs.tweeter.shared.model.domain.service.LogoutService;
import byu.edu.cs.tweeter.shared.request.LogoutRequest;
import byu.edu.cs.tweeter.shared.response.LogoutResponse;

public class LogoutServiceProxy extends Service implements LogoutService {
    public LogoutResponse logout(LogoutRequest request) {
        return getServerFacade().logout(request);
    }

}
