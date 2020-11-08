package byu.edu.cs.tweeter.shared.response;

import java.util.List;

import byu.edu.cs.tweeter.shared.model.domain.Status;

public class FeedResponse extends PagedResponse {
    private List<Status> feed;

    public FeedResponse(String message) {
        super(false, message, false);
        this.feed = null;
    }
    public FeedResponse(List<Status> statuses, boolean hasMorePages) {
        super(true, hasMorePages);
    }

    public List<Status> getFeed() {
        return feed;
    }

}
