package byu.edu.cs.tweeter.server.service;

import byu.edu.cs.tweeter.server.dao.AuthTokenDAO;
import byu.edu.cs.tweeter.server.dao.FollowDAO;
import byu.edu.cs.tweeter.server.dao.UserDAO;
import byu.edu.cs.tweeter.shared.model.domain.service.FollowActionService;
import byu.edu.cs.tweeter.shared.request.FollowUserRequest;
import byu.edu.cs.tweeter.shared.request.UnfollowUserRequest;
import byu.edu.cs.tweeter.shared.response.FollowUserResponse;
import byu.edu.cs.tweeter.shared.response.UnfollowUserResponse;

public class FollowActionServiceImpl implements FollowActionService
{
    @Override
    public FollowUserResponse followUser(FollowUserRequest request) {
        if ((request.getFollowee() == null) || (request.getFollower() == null)) {
            return new FollowUserResponse(false, "Bad Request: missing follower or followee alias..");
        }
        if (request.getAuthToken() == null) {
            return new FollowUserResponse(false, "Bad Request: no authentication..");
        }
        // TODO: validate the authtoken first, then allow the follow
        boolean correctToken = getAuthTokenDAO().validateAuthToken(request.getAuthToken(), request.getFollower().getAlias());
        if (!correctToken) {
            return new FollowUserResponse(false, "Failure: Login Expired! Please log in again..");
        }
        // increment the follower and followee counts
        getUserDAO().incrementFolloweeCount(request.getFollower().getAlias());
        getUserDAO().incrementFollowerCount(request.getFollowee().getAlias());
        return getFollowDAO().followUser(request);
    }

    @Override
    public UnfollowUserResponse unfollowUser(UnfollowUserRequest request) {
        if ((request.getFollowee() == null) || (request.getFollower() == null)) {
            return new UnfollowUserResponse(false, "Bad Request: missing follower or followee alias..");
        }
        if (request.getAuthToken() == null) {
            return new UnfollowUserResponse(false, "Bad Request: no authentication..");
        }
        // TODO: validate the authtoken first, then allow the unfollow
        boolean correctToken = getAuthTokenDAO().validateAuthToken(request.getAuthToken(), request.getFollower().getAlias());
        if (!correctToken) {
            return new UnfollowUserResponse(false, "Failure: Login Expired! Please log in again..");
        }
        // decrement the follower and followee counts
        getUserDAO().decrementFolloweeCount(request.getFollower().getAlias());
        getUserDAO().decrementFollowerCount(request.getFollowee().getAlias());
        return getFollowDAO().unfollowUser(request);
    }

    public FollowDAO getFollowDAO() {
        return new FollowDAO();
    }
    public AuthTokenDAO getAuthTokenDAO() {return new AuthTokenDAO();}
    public UserDAO getUserDAO() {return new UserDAO();}
}
