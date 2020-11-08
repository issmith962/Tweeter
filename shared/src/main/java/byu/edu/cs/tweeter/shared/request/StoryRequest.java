package byu.edu.cs.tweeter.shared.request;

import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.Status;
import byu.edu.cs.tweeter.shared.model.domain.User;

public class StoryRequest {
    private User user;
    private int limit;
    private Status lastStatus;
    private AuthToken authToken;

    public StoryRequest(User user, int limit, Status lastStatus, AuthToken authToken) {
        this.user = user;
        this.limit = limit;
        this.lastStatus = lastStatus;
        this.authToken = authToken;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }
    public User getUser() {
        return user;
    }

    public int getLimit() {
        return limit;
    }

    public Status getLastStatus() {
        return lastStatus;
    }

    public StoryRequest() {
        user = null;
        limit = 0;
        lastStatus = null;
        authToken = null;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setLastStatus(Status lastStatus) {
        this.lastStatus = lastStatus;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
