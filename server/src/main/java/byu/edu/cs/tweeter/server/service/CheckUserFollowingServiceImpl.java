package byu.edu.cs.tweeter.server.service;

import byu.edu.cs.tweeter.server.dao.FolloweesDAO;
import byu.edu.cs.tweeter.server.dao.FollowersDAO;
import byu.edu.cs.tweeter.shared.model.domain.service.CheckUserFollowingService;
import byu.edu.cs.tweeter.shared.request.CheckUserFollowingRequest;
import byu.edu.cs.tweeter.shared.response.CheckUserFollowingResponse;

public class CheckUserFollowingServiceImpl implements CheckUserFollowingService {
    @Override
    public CheckUserFollowingResponse isUserFollowing(CheckUserFollowingRequest request) {
        if ((request.getFolloweeAlias() == null) != (request.getFollowerAlias() == null)) {
            return new CheckUserFollowingResponse("Bad Request: Missing follower or followee alias..");
        }
        if ((request.getFolloweeAlias().equals("")) != (request.getFollowerAlias().equals(""))) {
            return new CheckUserFollowingResponse("Bad Request: follower and followee aliases cannot be empty..");
        }
        if (request.getFollowerAlias().equals(request.getFolloweeAlias())) {
            return new CheckUserFollowingResponse("Bad Request: User cannot be following self..");
        }

        if ((getFolloweesDAO().checkFollow(request.getFollowerAlias(), request.getFolloweeAlias()))
            && (getFollowersDAO().checkFollow(request.getFolloweeAlias(), request.getFollowerAlias()))) {
            return new CheckUserFollowingResponse(true);
        }
        else {
            return new CheckUserFollowingResponse(false);
        }
    }

    public FollowersDAO getFollowersDAO() {
        return new FollowersDAO();
    }
    public FolloweesDAO getFolloweesDAO() {return new FolloweesDAO(); }
}
