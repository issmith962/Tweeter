package byu.edu.cs.tweeter.server.service;

import byu.edu.cs.tweeter.server.dao.AuthTokenDAO;
import byu.edu.cs.tweeter.server.dao.FeedDAO;
import byu.edu.cs.tweeter.server.dao.FolloweesDAO;
import byu.edu.cs.tweeter.server.dao.FollowersDAO;
import byu.edu.cs.tweeter.server.dao.StoryDAO;
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
            getFolloweesDAO().addFollow(request.getFollower(), request.getFollowee());
            getFollowersDAO().addFollow(request.getFollowee(), request.getFollower());
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new FollowUserResponse(false, "Failure: Error in trying to adding follow relationship to database");
        }
        // increment the follower and followee counts
        getUserDAO().incrementFolloweeCount(request.getFollower().getAlias());
        getUserDAO().incrementFollowerCount(request.getFollowee().getAlias());

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
            getFolloweesDAO().removeFollow(request.getFollower(), request.getFollowee());
            getFollowersDAO().removeFollow(request.getFollowee(), request.getFollower());
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new UnfollowUserResponse(false, "Failure: Error in trying to remove follow relationship from database");
        }
        // decrement the follower and followee counts
        getUserDAO().decrementFolloweeCount(request.getFollower().getAlias());
        getUserDAO().decrementFollowerCount(request.getFollowee().getAlias());

        return new UnfollowUserResponse(true);
    }

    public AuthTokenDAO getAuthTokenDAO() {return new AuthTokenDAO();}
    public UserDAO getUserDAO() {return new UserDAO();}
    public FollowersDAO getFollowersDAO() {return new FollowersDAO();}
    public FolloweesDAO getFolloweesDAO() {return new FolloweesDAO();}
    public FeedDAO getFeedDAO() {return new FeedDAO();}
    public StoryDAO getStoryDAO() {return new StoryDAO();}
}


