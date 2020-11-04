package edu.byu.cs.tweeter.Client.model.services;

import edu.byu.cs.tweeter.Client.net.ServerFacade;
import byu.edu.cs.tweeter.shared.request.StoryRequest;
import byu.edu.cs.tweeter.shared.response.StoryResponse;

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
