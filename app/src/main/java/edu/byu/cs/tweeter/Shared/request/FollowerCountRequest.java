package edu.byu.cs.tweeter.Shared.request;

import edu.byu.cs.tweeter.Shared.domain.User;

public class FollowerCountRequest {
    private final User user;

    public FollowerCountRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}