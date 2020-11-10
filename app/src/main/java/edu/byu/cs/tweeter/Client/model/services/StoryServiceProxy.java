package edu.byu.cs.tweeter.Client.model.services;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.model.domain.service.StoryService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.StoryRequest;
import byu.edu.cs.tweeter.shared.response.StoryResponse;

public class StoryServiceProxy extends Service implements StoryService {
    private static final String URL_PATH = "/getstory";
    public StoryResponse getStory(StoryRequest request) throws IOException, TweeterRemoteException {
        String param_alias = "/" + request.getUser().getAlias();
        String newUrlPath = URL_PATH + param_alias;
        return getServerFacade().getStory(request, newUrlPath);
    }
}
