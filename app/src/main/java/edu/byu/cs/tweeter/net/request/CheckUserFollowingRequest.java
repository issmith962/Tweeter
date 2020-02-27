package edu.byu.cs.tweeter.net.request;

import edu.byu.cs.tweeter.model.domain.User;

public class CheckUserFollowingRequest {
    private User follower;
    private String followeeAlias;

    public CheckUserFollowingRequest(User follower, String followee) {
        this.follower = follower;
        this.followeeAlias = followee;
    }

    public User getFollower() {
        return follower;
    }

    public String getFolloweeAlias() {
        return followeeAlias;
    }
}
