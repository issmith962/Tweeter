package edu.byu.cs.tweeter.Client.model.services;

import byu.edu.cs.tweeter.shared.service.StoryService;
import edu.byu.cs.tweeter.Client.net.ServerFacade;
import byu.edu.cs.tweeter.shared.request.StoryRequest;
import byu.edu.cs.tweeter.shared.response.StoryResponse;

public class StoryServiceProxy extends Service implements StoryService {
    public StoryResponse getStory(StoryRequest request) {
        return getServerFacade().getStory(request);
    }
}
