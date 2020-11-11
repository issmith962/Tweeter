package byu.edu.cs.tweeter.server.service;

import byu.edu.cs.tweeter.server.dao.FolloweesDAO;
import byu.edu.cs.tweeter.shared.model.domain.service.FollowingService;
import byu.edu.cs.tweeter.shared.request.FollowingRequest;
import byu.edu.cs.tweeter.shared.response.FollowingResponse;

public class FollowingServiceImpl implements FollowingService {
    @Override
    public FollowingResponse getFollowees(FollowingRequest request) throws AssertionError {
        return getFolloweesDAO().getFollowees(request);
    }

    FolloweesDAO getFolloweesDAO() {
        return new FolloweesDAO();
    }

}
