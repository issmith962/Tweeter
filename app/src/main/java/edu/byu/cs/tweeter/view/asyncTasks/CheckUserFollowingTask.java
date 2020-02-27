package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.net.request.CheckUserFollowingRequest;
import edu.byu.cs.tweeter.net.response.CheckUserFollowingResponse;
import edu.byu.cs.tweeter.presenter.CheckUserFollowingPresenter;
import edu.byu.cs.tweeter.presenter.FollowingPresenter;

public class CheckUserFollowingTask extends AsyncTask<CheckUserFollowingRequest, Void, CheckUserFollowingResponse> {
    private final FollowingPresenter presenter;
    private final CheckUserFollowingObserver observer;

    public interface CheckUserFollowingObserver {
        void userFollowingChecked(CheckUserFollowingResponse response);
    }

    public CheckUserFollowingTask(FollowingPresenter presenter, CheckUserFollowingObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected CheckUserFollowingResponse doInBackground(CheckUserFollowingRequest... requests) {
        return presenter.isUserFollowing(requests[0]);
    }

    @Override
    protected void onPostExecute(CheckUserFollowingResponse response) {
        if (observer != null) {
            observer.userFollowingChecked(response);
        }
    }

}
