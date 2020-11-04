package edu.byu.cs.tweeter.Client.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import edu.byu.cs.tweeter.Client.model.services.PostStatusService;
import edu.byu.cs.tweeter.Shared.request.PostStatusRequest;
import edu.byu.cs.tweeter.Shared.response.PostStatusResponse;

/**
 * The presenter for the main activity.
 */
public class MainPresenter extends Presenter {

    private final View view;

    /**
     * The interface by which this presenter sends notifications to it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public MainPresenter(View view) {
        this.view = view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public PostStatusResponse postStatus(PostStatusRequest request) {
        return PostStatusService.getInstance().postStatus(request);
    }

}
