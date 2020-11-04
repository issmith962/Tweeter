package edu.byu.cs.tweeter.Shared.request;

import edu.byu.cs.tweeter.Shared.domain.User;

public class FolloweeCountRequest {
    private final User user;

    public FolloweeCountRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}