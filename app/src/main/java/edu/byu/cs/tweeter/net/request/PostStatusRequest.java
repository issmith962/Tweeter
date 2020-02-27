package edu.byu.cs.tweeter.net.request;

import edu.byu.cs.tweeter.model.domain.User;

public class PostStatusRequest {
    private User user;
    private String newStatus;
    private String date;

    public PostStatusRequest(User user, String newStatus, String date) {
        this.user = user;
        this.newStatus = newStatus;
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public String getDate() {
        return date;
    }
}
