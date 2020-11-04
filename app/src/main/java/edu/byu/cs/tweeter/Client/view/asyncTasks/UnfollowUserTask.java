package edu.byu.cs.tweeter.Client.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.Client.presenter.VisitorPresenter;
import byu.edu.cs.tweeter.shared.request.UnfollowUserRequest;
import byu.edu.cs.tweeter.shared.response.UnfollowUserResponse;

public class UnfollowUserTask extends AsyncTask<UnfollowUserRequest, Void, UnfollowUserResponse> {
    private final VisitorPresenter presenter;
    private final UnfollowUserObserver observer;

    public interface UnfollowUserObserver {
        void unfollowUserAttempted(UnfollowUserResponse response);
    }

    public UnfollowUserTask(VisitorPresenter presenter, UnfollowUserObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected UnfollowUserResponse doInBackground(UnfollowUserRequest... unfollowUserRequests) {
        UnfollowUserResponse response = presenter.unfollowUser(unfollowUserRequests[0]);
        return response;
    }

    @Override
    protected void onPostExecute(UnfollowUserResponse response) {
        if (observer != null) {
            observer.unfollowUserAttempted(response);
        }
    }
}
