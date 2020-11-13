package byu.edu.cs.tweeter.shared.response;

import java.util.List;

import byu.edu.cs.tweeter.shared.model.domain.Status;

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

    public void setStory(List<Status> story) {
        this.story = story;
    }
}
