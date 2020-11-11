package byu.edu.cs.tweeter.server.service;

import byu.edu.cs.tweeter.server.dao.FollowDAO;
import byu.edu.cs.tweeter.shared.model.domain.service.CheckUserFollowingService;
import byu.edu.cs.tweeter.shared.request.CheckUserFollowingRequest;
import byu.edu.cs.tweeter.shared.response.CheckUserFollowingResponse;

public class CheckUserFollowingServiceImpl implements CheckUserFollowingService {
    @Override
    public CheckUserFollowingResponse isUserFollowing(CheckUserFollowingRequest request) {
        return getFollowDAO().isUserFollowing(request);
    }

    FollowDAO getFollowDAO() {
        return new FollowDAO();
    }
}
