package edu.byu.cs.tweeter.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class PostStatusRequest {
    private User user;
    private String newStatus;
    private String date;
    private AuthToken authToken;

    public PostStatusRequest(User user, String newStatus, String date, AuthToken authToken) {
        this.user = user;
        this.newStatus = newStatus;
        this.date = date;
        this.authToken = authToken;
    }

    public AuthToken getAuthToken() {
        return authToken;
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
