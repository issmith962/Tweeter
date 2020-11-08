package byu.edu.cs.tweeter.shared.request;

import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.User;

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

    public CheckUserFollowingRequest() {
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public void setFolloweeAlias(String followeeAlias) {
        this.followeeAlias = followeeAlias;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
