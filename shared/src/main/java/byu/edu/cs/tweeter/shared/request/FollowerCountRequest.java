package byu.edu.cs.tweeter.shared.request;

import byu.edu.cs.tweeter.shared.model.domain.User;

public class FollowerCountRequest {
    private User user;

    public FollowerCountRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public FollowerCountRequest() {
    }

    public void setUser(User user) {
        this.user = user;
    }
}