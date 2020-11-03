package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.net.request.CheckUserFollowingRequest;
import edu.byu.cs.tweeter.net.response.CheckUserFollowingResponse;
import edu.byu.cs.tweeter.presenter.FollowingPresenter;
import edu.byu.cs.tweeter.presenter.Presenter;
import edu.byu.cs.tweeter.presenter.VisitorPresenter;

public class CheckUserFollowingTask extends AsyncTask<CheckUserFollowingRequest, Void, CheckUserFollowingResponse> {
    private final Presenter presenter;
    private final CheckUserFollowingObserver observer;

    public interface CheckUserFollowingObserver {
        void userFollowingChecked(CheckUserFollowingResponse response);
    }

    public CheckUserFollowingTask(FollowingPresenter presenter, CheckUserFollowingObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    public CheckUserFollowingTask(VisitorPresenter presenter, CheckUserFollowingObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected CheckUserFollowingResponse doInBackground(CheckUserFollowingRequest... requests) {
        if (presenter instanceof FollowingPresenter) {
            return ((FollowingPresenter) presenter).isUserFollowing(requests[0]);
        }
        else if (presenter instanceof VisitorPresenter) {
            return ((VisitorPresenter) presenter).isUserFollowing(requests[0]);
        }
        else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(CheckUserFollowingResponse response) {
        if (observer != null) {
            observer.userFollowingChecked(response);
        }
    }

}
