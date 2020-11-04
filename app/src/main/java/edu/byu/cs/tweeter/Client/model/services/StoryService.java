package edu.byu.cs.tweeter.Client.model.services;

import edu.byu.cs.tweeter.Client.net.ServerFacade;
import edu.byu.cs.tweeter.Shared.request.StoryRequest;
import edu.byu.cs.tweeter.Shared.response.StoryResponse;

public class StoryService {

    private static StoryService instance;
    private final ServerFacade serverFacade;

    public static StoryService getInstance() {
        if (instance == null) {
            instance = new StoryService();
        }
        return instance;
    }

    private StoryService() {
        serverFacade = new ServerFacade();
    }

    public StoryResponse getStory(StoryRequest request) {
        return serverFacade.getStory(request);
    }
}
