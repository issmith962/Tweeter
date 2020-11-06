package edu.byu.cs.tweeter.Client.presenter;

import edu.byu.cs.tweeter.Client.model.services.CheckUserFollowingServiceProxy;
import edu.byu.cs.tweeter.Client.model.services.FollowingServiceProxy;
import byu.edu.cs.tweeter.shared.request.CheckUserFollowingRequest;
import byu.edu.cs.tweeter.shared.request.FollowingRequest;
import byu.edu.cs.tweeter.shared.response.CheckUserFollowingResponse;
import byu.edu.cs.tweeter.shared.response.FollowingResponse;

/**
 * The presenter for the "following" functionality of the application.
 */
public class FollowingPresenter extends Presenter {

    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public FollowingPresenter(View view) {
        this.view = view;
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public FollowingResponse getFollowing(FollowingRequest request) throws AssertionError {
        return (new FollowingServiceProxy()).getFollowees(request);
    }

    public CheckUserFollowingResponse isUserFollowing(CheckUserFollowingRequest request) {
        return (new CheckUserFollowingServiceProxy()).isUserFollowing(request);
    }
}
