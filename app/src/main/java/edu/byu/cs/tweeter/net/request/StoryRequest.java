package edu.byu.cs.tweeter.net.request;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryRequest {
    private final User user;
    private final int limit;
    private final Status lastStatus;
    private String authToken;

    public StoryRequest(User user, int limit, Status lastStatus) {
        this.user = user;
        this.limit = limit;
        this.lastStatus = lastStatus;
        this.authToken = authToken;
    }

    public String getAuthToken() {
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
}
