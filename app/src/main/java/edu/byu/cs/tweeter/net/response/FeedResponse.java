package edu.byu.cs.tweeter.net.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;

public class FeedResponse extends PagedResponse {
    private List<Status> feed;

    public FeedResponse(String message) {
        super(false, message, false);
    }
    public FeedResponse(List<Status> statuses, boolean hasMorePages) {
        super(true, hasMorePages);
        this.feed = statuses;
    }

    public List<Status> getFeed() {
        return feed;
    }

}
