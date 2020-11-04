package byu.edu.cs.tweeter.shared.request;

import byu.edu.cs.tweeter.shared.domain.AuthToken;
import byu.edu.cs.tweeter.shared.domain.Status;
import byu.edu.cs.tweeter.shared.domain.User;

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
