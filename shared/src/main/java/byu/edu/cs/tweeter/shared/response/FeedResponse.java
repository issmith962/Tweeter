package byu.edu.cs.tweeter.shared.response;

import java.util.List;
import java.util.Objects;

import byu.edu.cs.tweeter.shared.model.domain.Status;

public class FeedResponse extends PagedResponse {
    private List<Status> feed;

    public FeedResponse(String message) {
        super(false, message, false);
        this.feed = null;
    }
    public FeedResponse(List<Status> statuses, boolean hasMorePages) {
        super(true, hasMorePages);
        this.feed = statuses;
    }
    public List<Status> getFeed() {
        return feed;
    }

    public void setFeed(List<Status> feed) {
        this.feed = feed;
    }

    public FeedResponse() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FeedResponse that = (FeedResponse) o;
        return Objects.equals(feed, that.feed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), feed);
    }
}
