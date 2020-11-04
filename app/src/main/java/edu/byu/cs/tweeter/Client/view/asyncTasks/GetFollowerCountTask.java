package edu.byu.cs.tweeter.Client.view.asyncTasks;

import android.os.AsyncTask;

import byu.edu.cs.tweeter.shared.request.FollowerCountRequest;
import byu.edu.cs.tweeter.shared.response.FollowerCountResponse;
import edu.byu.cs.tweeter.Client.presenter.Presenter;

public class GetFollowerCountTask extends AsyncTask<FollowerCountRequest, Void, FollowerCountResponse> {
    private final Presenter presenter;
    private final FollowerCountObserver observer;

    public interface FollowerCountObserver {
        void followerCountRequested(FollowerCountResponse response);
    }

    public GetFollowerCountTask(Presenter presenter, FollowerCountObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected FollowerCountResponse doInBackground(FollowerCountRequest... followerCountRequests) {
        FollowerCountResponse response = presenter.getFollowerCount(followerCountRequests[0]);
        return response;
    }

    @Override
    protected void onPostExecute(FollowerCountResponse response) {
        if (observer != null) {
            observer.followerCountRequested(response);
        }
    }
}
