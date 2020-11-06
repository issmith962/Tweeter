package byu.edu.cs.tweeter.shared.model.domain.service;

import byu.edu.cs.tweeter.shared.request.StoryRequest;
import byu.edu.cs.tweeter.shared.response.StoryResponse;

public interface StoryService {
    public StoryResponse getStory(StoryRequest request);
}
