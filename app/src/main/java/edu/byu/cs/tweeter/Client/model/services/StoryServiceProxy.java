package edu.byu.cs.tweeter.Client.model.services;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.model.domain.service.StoryService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.StoryRequest;
import byu.edu.cs.tweeter.shared.response.StoryResponse;
import edu.byu.cs.tweeter.Client.net.Exception.TweeterRequestException;

public class StoryServiceProxy extends Service implements StoryService {
    public static final String URL_PATH = "/getstory";
    public StoryResponse getStory(StoryRequest request) throws IOException, TweeterRemoteException {
        if (request.getUser() != null) {
            if ((request.getUser().getAlias() != null) && (!request.getUser().getAlias().equals(""))) {
                String param_alias = "/" + request.getUser().getAlias();
                String newUrlPath = URL_PATH + param_alias;
                return getServerFacade().getStory(request, newUrlPath);
            }
        }
        return new StoryResponse("Failure: User information invalid..");
    }
}
