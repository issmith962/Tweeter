package byu.edu.cs.tweeter.server.service;

import byu.edu.cs.tweeter.server.dao.AuthTokenDAO;
import byu.edu.cs.tweeter.server.dao.FollowDAO;
import byu.edu.cs.tweeter.server.dao.UserDAO;
import byu.edu.cs.tweeter.shared.model.domain.service.FollowActionService;
import byu.edu.cs.tweeter.shared.net.DataAccessException;
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
        boolean correctToken = getAuthTokenDAO().validateAuthToken(request.getAuthToken(), request.getFollower().getAlias());
        if (!correctToken) {
            return new FollowUserResponse(false, "Failure: Login Expired! Please log in again..");
        }
        try {
            getFollowDAO().addFollow(request.getFollowee(), request.getFollower());
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new FollowUserResponse(false, "Failure: Error in trying to adding follow relationship to database");
        }
        // increment the follower and followee counts
        try {
            getUserDAO().incrementFolloweeCount(request.getFollower().getAlias());
        } catch (DataAccessException e) {
            return new FollowUserResponse(false, e.getMessage());
        }
        try {
            getUserDAO().incrementFollowerCount(request.getFollowee().getAlias());
        } catch (DataAccessException e) {
            return new FollowUserResponse(false, e.getMessage());
        }

        return new FollowUserResponse(true);
    }

    @Override
    public UnfollowUserResponse unfollowUser(UnfollowUserRequest request) {
        if ((request.getFollowee() == null) || (request.getFollower() == null)) {
            return new UnfollowUserResponse(false, "Bad Request: missing follower or followee alias..");
        }
        if (request.getAuthToken() == null) {
            return new UnfollowUserResponse(false, "Bad Request: no authentication..");
        }
        boolean correctToken = getAuthTokenDAO().validateAuthToken(request.getAuthToken(), request.getFollower().getAlias());
        if (!correctToken) {
            return new UnfollowUserResponse(false, "Failure: Login Expired! Please log in again..");
        }
        try {
            getFollowDAO().removeFollow(request.getFollowee(), request.getFollower());
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new UnfollowUserResponse(false, "Failure: Error in trying to remove follow relationship from database");
        }
        // decrement the follower and followee counts
        try {
            getUserDAO().decrementFolloweeCount(request.getFollower().getAlias());
        } catch (DataAccessException e) {
            return new UnfollowUserResponse(false, e.getMessage());
        }
        try {
            getUserDAO().decrementFollowerCount(request.getFollowee().getAlias());
        } catch (DataAccessException e) {
            return new UnfollowUserResponse(false, e.getMessage());
        }

        return new UnfollowUserResponse(true);
    }

    public AuthTokenDAO getAuthTokenDAO() {return new AuthTokenDAO();}
    public UserDAO getUserDAO() {return new UserDAO();}
    public FollowDAO getFollowDAO() {
        return new FollowDAO();
    }
}


