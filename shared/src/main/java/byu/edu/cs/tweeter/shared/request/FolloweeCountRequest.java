package byu.edu.cs.tweeter.shared.request;

import byu.edu.cs.tweeter.shared.domain.User;

public class FolloweeCountRequest {
    private final User user;

    public FolloweeCountRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}