package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.net.request.FollowUserRequest;
import edu.byu.cs.tweeter.net.response.FollowUserResponse;
import edu.byu.cs.tweeter.presenter.VisitorPresenter;

public class FollowUserTask extends AsyncTask<FollowUserRequest, Void, FollowUserResponse> {
    private final VisitorPresenter presenter;
    private final FollowUserObserver observer;

    public interface FollowUserObserver {
        void followUserAttempted(FollowUserResponse response);
    }

    public FollowUserTask(VisitorPresenter presenter, FollowUserObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected FollowUserResponse doInBackground(FollowUserRequest... followUserRequests) {
        FollowUserResponse response = presenter.followUser(followUserRequests[0]);
        return response;
    }

    @Override
    protected void onPostExecute(FollowUserResponse response) {
        if (observer != null) {
            observer.followUserAttempted(response);
        }
    }
}
