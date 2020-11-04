package edu.byu.cs.tweeter.Shared.response;

import java.util.List;

import edu.byu.cs.tweeter.Shared.domain.Status;

public class StoryResponse extends PagedResponse {
    private List<Status> story;

    public StoryResponse(String message) {
        super(false, message, false);
    }
    public StoryResponse(List<Status> statuses, boolean hasMorePages) {
        super(true, hasMorePages);
        this.story = statuses;
    }

    public List<Status> getStory() {
        return story;
    }
}
