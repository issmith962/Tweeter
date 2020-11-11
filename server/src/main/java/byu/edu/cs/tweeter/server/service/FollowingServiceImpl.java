package byu.edu.cs.tweeter.server.service;

import java.io.IOException;

import byu.edu.cs.tweeter.server.dao.FolloweesDAO;
import byu.edu.cs.tweeter.shared.model.domain.service.FollowingService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.FolloweeCountRequest;
import byu.edu.cs.tweeter.shared.request.FollowingRequest;
import byu.edu.cs.tweeter.shared.response.FolloweeCountResponse;
import byu.edu.cs.tweeter.shared.response.FollowingResponse;

public class FollowingServiceImpl implements FollowingService {
    @Override
    public FollowingResponse getFollowees(FollowingRequest request) throws AssertionError {
        return getFolloweesDAO().getFollowees(request);
    }

    @Override
    public FolloweeCountResponse getFolloweeCount(FolloweeCountRequest request) throws IOException, TweeterRemoteException {
        return null;
    }

    FolloweesDAO getFolloweesDAO() {
        return new FolloweesDAO();
    }

}
