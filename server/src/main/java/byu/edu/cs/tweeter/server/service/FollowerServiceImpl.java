package byu.edu.cs.tweeter.server.service;

import byu.edu.cs.tweeter.server.dao.FollowersDAO;
import byu.edu.cs.tweeter.server.dao.UserDAO;
import byu.edu.cs.tweeter.shared.model.domain.service.FollowerService;
import byu.edu.cs.tweeter.shared.request.FollowerCountRequest;
import byu.edu.cs.tweeter.shared.request.FollowersRequest;
import byu.edu.cs.tweeter.shared.response.FollowerCountResponse;
import byu.edu.cs.tweeter.shared.response.FollowersResponse;

public class FollowerServiceImpl implements FollowerService {
    @Override
    public FollowersResponse getFollowers(FollowersRequest request) {
        if (request.getFollowee() == null) {
            return new FollowersResponse("Bad Request: no user given..");
        }
        if (request.getLimit() == 0) {
            return new FollowersResponse("Bad Request: no followers requested..");
        }
        return getFollowersDAO().getFollowers(request);
    }

    @Override
    public FollowerCountResponse getFollowerCount(FollowerCountRequest request) {
        if (request.getUser() == null) {
            return new FollowerCountResponse("Bad Request: no user given..");
        }
        int followerCount = getUserDAO().getFollowerCount(request.getUser().getAlias());
        return new FollowerCountResponse(followerCount);
    }

    public UserDAO getUserDAO() {return new UserDAO();}
    public FollowersDAO getFollowersDAO() {return new FollowersDAO();}
}

