package byu.edu.cs.tweeter.server.service;

import byu.edu.cs.tweeter.server.dao.FollowDAO;
import byu.edu.cs.tweeter.shared.model.domain.service.FollowerService;
import byu.edu.cs.tweeter.shared.request.FollowerCountRequest;
import byu.edu.cs.tweeter.shared.request.FollowersRequest;
import byu.edu.cs.tweeter.shared.response.FollowerCountResponse;
import byu.edu.cs.tweeter.shared.response.FollowersResponse;

public class FollowerServiceImpl implements FollowerService {
    @Override
    public FollowersResponse getFollowers(FollowersRequest request) {
        return getFollowDAO().getFollowers(request);
    }

    @Override
    public FollowerCountResponse getFollowerCount(FollowerCountRequest request) {
        Integer followerCount = getFollowDAO().getFollowerCount(request);
        return new FollowerCountResponse(followerCount);
    }

    FollowDAO getFollowDAO() {
        return new FollowDAO();
    }
}
