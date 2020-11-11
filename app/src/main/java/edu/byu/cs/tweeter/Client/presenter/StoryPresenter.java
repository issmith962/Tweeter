package edu.byu.cs.tweeter.Client.presenter;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.Client.model.services.StoryServiceProxy;
import byu.edu.cs.tweeter.shared.request.StoryRequest;
import byu.edu.cs.tweeter.shared.response.StoryResponse;

public class StoryPresenter extends Presenter {
    private final View view;

    public interface View {
        // if needed, specify methods here that will be called on the view in
        // response to model updates
    }
    public StoryPresenter(View view) {
        this.view = view;
    }
    public StoryResponse getStory(StoryRequest request) throws IOException, TweeterRemoteException {
        return (new StoryServiceProxy()).getStory(request);
    }

    public AuthToken findCurrentAuthToken() {
        return getCurrentAuthToken();
    }

}
