package edu.byu.cs.tweeter.Client.view.asyncTasks;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import byu.edu.cs.tweeter.shared.request.StartUpRequest;
import byu.edu.cs.tweeter.shared.response.StartUpResponse;
import edu.byu.cs.tweeter.Client.presenter.LoginPresenter;

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
    @RequiresApi(api = Build.VERSION_CODES.O)
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
