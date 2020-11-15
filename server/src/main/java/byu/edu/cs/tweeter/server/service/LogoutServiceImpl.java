package byu.edu.cs.tweeter.server.service;

import byu.edu.cs.tweeter.server.dao.AuthTokenDAO;
import byu.edu.cs.tweeter.shared.model.domain.service.LogoutService;
import byu.edu.cs.tweeter.shared.request.LogoutRequest;
import byu.edu.cs.tweeter.shared.response.LogoutResponse;

public class LogoutServiceImpl implements LogoutService {
    @Override
    public LogoutResponse logout(LogoutRequest request) {
        if (request.getAuthToken() == null) {
            return new LogoutResponse(false, "Bad Request: no authentication given..");
        }
        getAuthTokenDAO().deleteAuthToken(request.getAuthToken());
        return new LogoutResponse(true);
    }

    public AuthTokenDAO getAuthTokenDAO() {
        return new AuthTokenDAO();
    }
}
