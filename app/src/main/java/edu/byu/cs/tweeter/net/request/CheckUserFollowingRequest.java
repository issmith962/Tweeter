package edu.byu.cs.tweeter.net.request;

import edu.byu.cs.tweeter.model.domain.User;

public class CheckUserFollowingRequest {
    private User follower;
    private String followeeAlias;
    private String authToken;

    public CheckUserFollowingRequest(User follower, String followeeAlias, String authToken) {
        this.follower = follower;
        this.followeeAlias = followeeAlias;
        this.authToken = authToken;
    }

    public User getFollower() {
        return follower;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getFolloweeAlias() {
        return followeeAlias;
    }
}
