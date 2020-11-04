package byu.edu.cs.tweeter.shared.request;

import byu.edu.cs.tweeter.shared.domain.User;

public class FollowerCountRequest {
    private final User user;

    public FollowerCountRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}