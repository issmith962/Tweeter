package byu.edu.cs.tweeter.shared.request;

import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.User;

public class UnfollowUserRequest {
    private User follower;
    private User followee;
    private AuthToken authToken;
    public UnfollowUserRequest(User follower, User followee, AuthToken authToken) {
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

    public UnfollowUserRequest() {
        followee = null;
        follower = null;
        authToken = null;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public void setFollowee(User followee) {
        this.followee = followee;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}