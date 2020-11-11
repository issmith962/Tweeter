package edu.byu.cs.tweeter.Client.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.FollowUserRequest;
import byu.edu.cs.tweeter.shared.response.FollowUserResponse;
import edu.byu.cs.tweeter.Client.presenter.VisitorPresenter;

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
        FollowUserResponse response = null;
        try {
            response = presenter.followUser(followUserRequests[0]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(FollowUserResponse response) {
        if (observer != null) {
            observer.followUserAttempted(response);
        }
    }
}
