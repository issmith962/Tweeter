package byu.edu.cs.tweeter.shared.request;

import byu.edu.cs.tweeter.shared.model.domain.Status;

public class PostUpdateFeedMessagesRequest {
    private Status status;

    public PostUpdateFeedMessagesRequest(Status status) {
        this.status = status;
    }

    public PostUpdateFeedMessagesRequest() {
        status = null;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
