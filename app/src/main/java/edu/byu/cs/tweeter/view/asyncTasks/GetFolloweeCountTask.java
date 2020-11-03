package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.net.request.FolloweeCountRequest;
import edu.byu.cs.tweeter.net.response.FolloweeCountResponse;
import edu.byu.cs.tweeter.presenter.Presenter;

public class GetFolloweeCountTask extends AsyncTask<FolloweeCountRequest, Void, FolloweeCountResponse> {
    private final Presenter presenter;
    private final FolloweeCountObserver observer;

    public interface FolloweeCountObserver {
        void followeeCountRequested(FolloweeCountResponse response);
    }

    public GetFolloweeCountTask(Presenter presenter, FolloweeCountObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected FolloweeCountResponse doInBackground(FolloweeCountRequest... followeeCountRequests) {
        FolloweeCountResponse response = presenter.getFolloweeCount(followeeCountRequests[0]);
        return response;
    }

    @Override
    protected void onPostExecute(FolloweeCountResponse response) {
        if (observer != null) {
            observer.followeeCountRequested(response);
        }
    }
}
