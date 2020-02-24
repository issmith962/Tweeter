package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.services.StoryService;
import edu.byu.cs.tweeter.net.request.StoryRequest;
import edu.byu.cs.tweeter.net.response.StoryResponse;

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
