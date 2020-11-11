package edu.byu.cs.tweeter.Client.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.LogoutRequest;
import byu.edu.cs.tweeter.shared.response.LogoutResponse;
import edu.byu.cs.tweeter.Client.presenter.Presenter;

public class LogoutTask extends AsyncTask<LogoutRequest, Void, LogoutResponse> {
    private final Presenter presenter;
    private final LogoutObserver observer;

    public interface LogoutObserver {
        void logoutAttempted(LogoutResponse response);
    }

    public LogoutTask(Presenter presenter, LogoutObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected LogoutResponse doInBackground(LogoutRequest... logoutRequests) {
        try {
            return presenter.logout(logoutRequests[0]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(LogoutResponse response) {
        if (observer != null) {
            observer.logoutAttempted(response);
        }
    }
}
