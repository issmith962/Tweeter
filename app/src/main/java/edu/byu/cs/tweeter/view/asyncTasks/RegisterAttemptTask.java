package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.net.request.RegisterRequest;
import edu.byu.cs.tweeter.net.response.RegisterResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;

public class RegisterAttemptTask extends AsyncTask<RegisterRequest, Void, RegisterResponse> {
    private final LoginPresenter presenter;
    private final RegisterAttemptObserver observer;

    public interface RegisterAttemptObserver {
        void registerAttempted(RegisterResponse registerResponse);
    }

    public RegisterAttemptTask(LoginPresenter presenter, RegisterAttemptObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected RegisterResponse doInBackground(RegisterRequest... registerRequests) {
        RegisterResponse response = presenter.registerUser(registerRequests[0]);
        return response;
    }

    @Override
    protected void onPostExecute(RegisterResponse response) {
        if (observer != null) {
            observer.registerAttempted(response);
        }
    }

}
