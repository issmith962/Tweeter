package byu.edu.cs.tweeter.shared.request;

import byu.edu.cs.tweeter.shared.model.domain.User;

public class FolloweeCountRequest {
    private User user;

    public FolloweeCountRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FolloweeCountRequest() {
    }
}