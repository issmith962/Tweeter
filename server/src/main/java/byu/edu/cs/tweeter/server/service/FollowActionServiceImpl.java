package byu.edu.cs.tweeter.server.service;

import byu.edu.cs.tweeter.server.dao.AuthTokenDAO;
import byu.edu.cs.tweeter.server.dao.FollowDAO;
import byu.edu.cs.tweeter.shared.model.domain.service.FollowActionService;
import byu.edu.cs.tweeter.shared.request.FollowUserRequest;
import byu.edu.cs.tweeter.shared.request.UnfollowUserRequest;
import byu.edu.cs.tweeter.shared.response.FollowUserResponse;
import byu.edu.cs.tweeter.shared.response.UnfollowUserResponse;

public class FollowActionServiceImpl implements FollowActionService
{
    @Override
    public FollowUserResponse followUser(FollowUserRequest request) {
        // TODO: validate the authtoken first, then allow the follow
        boolean correctToken = getAuthTokenDAO().validateAuthToken(request.getAuthToken(), request.getFollower().getAlias());
        if (!correctToken) {
            return new FollowUserResponse(false, "Failure: Login Expired! Please log in again..");
        }
        return getFollowDAO().followUser(request);
    }

    @Override
    public UnfollowUserResponse unfollowUser(UnfollowUserRequest request) {
        // TODO: validate the authtoken first, then allow the unfollow
        boolean correctToken = getAuthTokenDAO().validateAuthToken(request.getAuthToken(), request.getFollower().getAlias());
        if (!correctToken) {
            return new UnfollowUserResponse(false, "Failure: Login Expired! Please log in again..");
        }
        return getFollowDAO().unfollowUser(request);
    }

    FollowDAO getFollowDAO() {
        return new FollowDAO();
    }
    AuthTokenDAO getAuthTokenDAO() {return new AuthTokenDAO();}
}
