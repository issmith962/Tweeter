package edu.byu.cs.tweeter.Shared.request;

import edu.byu.cs.tweeter.Shared.domain.AuthToken;
import edu.byu.cs.tweeter.Shared.domain.User;

public class FollowUserRequest {
    private User follower;
    private User followee;
    private AuthToken authToken;

    public FollowUserRequest(User follower, User followee, AuthToken authToken) {
        this.follower = follower;
        this.followee = followee;
        this.authToken = authToken;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public User getFollower() {
        return follower;
    }

    public User getFollowee() {
        return followee;
    }
}
