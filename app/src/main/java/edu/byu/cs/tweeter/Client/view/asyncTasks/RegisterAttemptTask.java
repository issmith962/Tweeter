package edu.byu.cs.tweeter.Client.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.Client.presenter.LoginPresenter;
import edu.byu.cs.tweeter.Shared.request.RegisterRequest;
import edu.byu.cs.tweeter.Shared.response.RegisterResponse;

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
