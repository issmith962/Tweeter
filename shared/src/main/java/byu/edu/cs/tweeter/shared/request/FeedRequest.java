package byu.edu.cs.tweeter.shared.request;

import byu.edu.cs.tweeter.shared.domain.Status;
import byu.edu.cs.tweeter.shared.domain.User;

public class FeedRequest {
    private final User user;
    private final int limit;
    private final Status lastStatus;

    public FeedRequest(User user, int limit, Status lastStatus) {
        this.user = user;
        this.limit = limit;
        this.lastStatus = lastStatus;
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
