package edu.byu.cs.tweeter.Client.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.Client.presenter.LoginPresenter;
import byu.edu.cs.tweeter.shared.request.RegisterRequest;
import byu.edu.cs.tweeter.shared.response.RegisterResponse;

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
        RegisterResponse response = null;
        try {
            response = presenter.registerUser(registerRequests[0]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(RegisterResponse response) {
        if (observer != null) {
            observer.registerAttempted(response);
        }
    }

}
