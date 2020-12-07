package byu.edu.cs.tweeter.shared.request;

import java.util.List;

import byu.edu.cs.tweeter.shared.model.domain.Status;
import byu.edu.cs.tweeter.shared.model.domain.User;

public class UpdateFeedRequest {
    private Status status;
    private List<User> followees;

    public UpdateFeedRequest(Status status, List<User> followees) {
        this.status = status;
        this.followees = followees;
    }

    public UpdateFeedRequest() {
        status = null;
        followees = null;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<User> getFollowees() {
        return followees;
    }

    public void setFollowees(List<User> followees) {
        this.followees = followees;
    }
}
