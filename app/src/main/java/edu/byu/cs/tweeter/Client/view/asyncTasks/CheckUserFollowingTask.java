package edu.byu.cs.tweeter.Client.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.CheckUserFollowingRequest;
import byu.edu.cs.tweeter.shared.response.CheckUserFollowingResponse;
import edu.byu.cs.tweeter.Client.presenter.FollowingPresenter;
import edu.byu.cs.tweeter.Client.presenter.Presenter;
import edu.byu.cs.tweeter.Client.presenter.VisitorPresenter;

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
            try {
                return ((FollowingPresenter) presenter).isUserFollowing(requests[0]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TweeterRemoteException e) {
                e.printStackTrace();
            }
        }
        else if (presenter instanceof VisitorPresenter) {
            try {
                return ((VisitorPresenter) presenter).isUserFollowing(requests[0]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TweeterRemoteException e) {
                e.printStackTrace();
            }
        }
        else {
            return null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(CheckUserFollowingResponse response) {
        if (observer != null) {
            observer.userFollowingChecked(response);
        }
    }

}
