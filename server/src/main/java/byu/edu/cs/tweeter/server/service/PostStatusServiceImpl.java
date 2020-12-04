package byu.edu.cs.tweeter.server.service;

import byu.edu.cs.tweeter.server.dao.AuthTokenDAO;
import byu.edu.cs.tweeter.server.dao.StatusDAO;
import byu.edu.cs.tweeter.shared.model.domain.service.PostStatusService;
import byu.edu.cs.tweeter.shared.request.PostStatusRequest;
import byu.edu.cs.tweeter.shared.response.PostStatusResponse;

public class PostStatusServiceImpl implements PostStatusService {
    @Override
    public PostStatusResponse postStatus(PostStatusRequest request) {
        if (request.getUser() == null) {
            return new PostStatusResponse(false, "Bad Request: no user given..");
        }
        if (request.getAuthToken() == null) {
            return new PostStatusResponse(false, "Bad Request: no authentication");
        }
        if (request.getPostingTimestamp() == 0) {
            return new PostStatusResponse(false, "Bad Request: timestamp is zero");
        }
        if (request.getNewStatus() == null) {
            return new PostStatusResponse(false, "Bad Request: new post cannot be empty");
        }
        if (request.getNewStatus().equals("")) {
            return new PostStatusResponse(false, "Bad Request: new post cannot be empty");
        }

        boolean correctAuthToken = getAuthTokenDAO().validateAuthToken(request.getAuthToken(), request.getUser().getAlias());
        if (!correctAuthToken) {
            return new PostStatusResponse(false,"Failure: Login expired! Please log in again..");
        }
        return getStatusDAO().postStatus(request);
    }

    public AuthTokenDAO getAuthTokenDAO() {
        return new AuthTokenDAO();
    }

    public StatusDAO getStatusDAO() {
        return new StatusDAO();
    }
}
