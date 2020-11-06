package byu.edu.cs.tweeter.shared.request;

import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.User;

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
