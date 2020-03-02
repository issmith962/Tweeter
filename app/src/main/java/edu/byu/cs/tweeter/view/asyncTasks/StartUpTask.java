package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.net.request.StartUpRequest;
import edu.byu.cs.tweeter.net.response.StartUpResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;

public class StartUpTask extends AsyncTask<StartUpRequest, Void, StartUpResponse> {
    private final LoginPresenter presenter;
    private final StartUpTaskObserver observer;

    public interface StartUpTaskObserver {
        void startUpCompleted(StartUpResponse response);
    }
    public StartUpTask(LoginPresenter presenter, StartUpTaskObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }
    @Override
    protected StartUpResponse doInBackground(StartUpRequest... startUpRequests) {
        StartUpResponse response = presenter.startUp(startUpRequests[0]);
        return response;
    }

    @Override
    protected void onPostExecute(StartUpResponse response) {
        if (observer != null) {
            observer.startUpCompleted(response);
        }
    }
}
