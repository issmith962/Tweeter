package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.services.CheckUserFollowingService;
import edu.byu.cs.tweeter.net.request.CheckUserFollowingRequest;
import edu.byu.cs.tweeter.net.response.CheckUserFollowingResponse;

public class CheckUserFollowingPresenter extends Presenter {
    private final View view;
    public interface View {
        // if needed, specify methods here to be called on view
    }
    public CheckUserFollowingPresenter(View view) {
        this.view = view;
    }

}
