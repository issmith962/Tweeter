package edu.byu.cs.tweeter.Client.presenter;

import edu.byu.cs.tweeter.Client.model.services.StoryService;
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
    public StoryResponse getStory(StoryRequest request) {
        return StoryService.getInstance().getStory(request);
    }
}
