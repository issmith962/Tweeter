package byu.edu.cs.tweeter.server.service;

import java.io.IOException;

import byu.edu.cs.tweeter.server.dao.FollowDAO;
import byu.edu.cs.tweeter.shared.model.domain.Follow;
import byu.edu.cs.tweeter.shared.model.domain.service.FollowingService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.FolloweeCountRequest;
import byu.edu.cs.tweeter.shared.request.FollowingRequest;
import byu.edu.cs.tweeter.shared.response.FolloweeCountResponse;
import byu.edu.cs.tweeter.shared.response.FollowingResponse;

public class FollowingServiceImpl implements FollowingService {
    @Override
    public FollowingResponse getFollowees(FollowingRequest request) throws AssertionError {
        if (request.getFollower() == null) {
            return new FollowingResponse("Bad Request: no user given..");
        }
        if (request.getLimit() == 0) {
            return new FollowingResponse("Bad Request: no followers requested..");
        }

        return getFollowDAO().getFollowees(request);
    }

    @Override
    public FolloweeCountResponse getFolloweeCount(FolloweeCountRequest request) {
        if (request.getUser() == null) {
            return new FolloweeCountResponse("Bad Request: no user given..");
        }

        Integer followeeCount = getFollowDAO().getFolloweeCount(request);
        return new FolloweeCountResponse(followeeCount);
    }

    public FollowDAO getFollowDAO() {
        return new FollowDAO();
    }

}
