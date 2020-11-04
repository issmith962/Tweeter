package edu.byu.cs.tweeter.Shared.request;

import edu.byu.cs.tweeter.Shared.domain.AuthToken;
import edu.byu.cs.tweeter.Shared.domain.User;

public class CheckUserFollowingRequest {
    private User follower;
    private String followeeAlias;
    private AuthToken authToken;

    public CheckUserFollowingRequest(User follower, String followeeAlias, AuthToken authToken) {
        this.follower = follower;
        this.followeeAlias = followeeAlias;
        this.authToken = authToken;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }
    public User getFollower() {
        return follower;
    }

    public String getFolloweeAlias() {
        return followeeAlias;
    }
}
