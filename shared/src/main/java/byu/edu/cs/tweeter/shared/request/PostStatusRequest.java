package byu.edu.cs.tweeter.shared.request;

import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.User;

public class PostStatusRequest {
    private User user;
    private String newStatus;
    private long postingTimestamp;
    private AuthToken authToken;

    public PostStatusRequest(User user, String newStatus, long postingTimestamp, AuthToken authToken) {
        this.user = user;
        this.newStatus = newStatus;
        this.postingTimestamp = postingTimestamp;
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

    public long getPostingTimestamp() {
        return postingTimestamp;
    }

    public PostStatusRequest() {
        user = null;
        newStatus = null;
        postingTimestamp = 0;
        authToken = null;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public void setPostingTimestamp(long postingTimestamp) {
        this.postingTimestamp = postingTimestamp;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
