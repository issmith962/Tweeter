package edu.byu.cs.tweeter.net.request;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowUserRequest {
    private User follower;
    private User followee;
    private String authToken;

    public FollowUserRequest(User follower, User followee) {
        this.follower = follower;
        this.followee = followee;
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public User getFollower() {
        return follower;
    }

    public User getFollowee() {
        return followee;
    }
}
