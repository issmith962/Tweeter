package edu.byu.cs.tweeter.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryRequest {
    private final User user;
    private final int limit;
    private final Status lastStatus;
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
}
