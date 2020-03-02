package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.net.request.FollowUserRequest;
import edu.byu.cs.tweeter.net.request.UnfollowUserRequest;
import edu.byu.cs.tweeter.net.response.FollowUserResponse;
import edu.byu.cs.tweeter.net.response.UnfollowUserResponse;
import edu.byu.cs.tweeter.presenter.VisitorPresenter;

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
