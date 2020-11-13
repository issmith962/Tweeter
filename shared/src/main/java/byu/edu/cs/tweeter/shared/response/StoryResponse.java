package byu.edu.cs.tweeter.shared.response;

import java.util.List;
import java.util.Objects;

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

    public StoryResponse() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StoryResponse that = (StoryResponse) o;
        return Objects.equals(story, that.story);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), story);
    }
}
