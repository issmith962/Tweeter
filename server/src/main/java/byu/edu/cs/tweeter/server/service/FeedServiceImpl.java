package byu.edu.cs.tweeter.server.service;

import java.util.List;

import byu.edu.cs.tweeter.server.dao.AuthTokenDAO;
import byu.edu.cs.tweeter.server.dao.FollowDAO;
import byu.edu.cs.tweeter.server.dao.StatusDAO;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.model.domain.service.FeedService;
import byu.edu.cs.tweeter.shared.request.FeedRequest;
import byu.edu.cs.tweeter.shared.response.FeedResponse;

public class FeedServiceImpl implements FeedService {
    @Override
    public FeedResponse getFeed(FeedRequest request) {
        boolean correctAuthToken = getAuthTokenDAO().validateAuthToken(request.getAuthToken(), request.getUser().getAlias());
        if (!correctAuthToken) {
            return new FeedResponse("Failure: Login expired! Please log in again..");
        }
        List<User> followees = getFollowDAO().getAllFollowees(request.getUser());
        return getStatusDAO().getFeed(request, followees);
    }

    AuthTokenDAO getAuthTokenDAO() {
        return new AuthTokenDAO();
    }

    FollowDAO getFollowDAO() {
        return new FollowDAO();
    }

    StatusDAO getStatusDAO() {
        return new StatusDAO();
    }
}
