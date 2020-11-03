package edu.byu.cs.tweeter.net.request;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowerCountRequest {
    private final User user;

    public FollowerCountRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}