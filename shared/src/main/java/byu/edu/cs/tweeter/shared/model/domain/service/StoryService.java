package byu.edu.cs.tweeter.shared.model.domain.service;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.StoryRequest;
import byu.edu.cs.tweeter.shared.response.StoryResponse;

public interface StoryService {
    public StoryResponse getStory(StoryRequest request) throws IOException, TweeterRemoteException;
}
