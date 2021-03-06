package edu.byu.cs.tweeter.Client.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.Client.presenter.LoginPresenter;
import byu.edu.cs.tweeter.shared.request.LoginRequest;
import byu.edu.cs.tweeter.shared.response.LoginResponse;

/**
 * An {@Link AsyncTask}  for attempting a login.
 */
public class LoginAttemptTask extends AsyncTask<LoginRequest, Void, LoginResponse> {
    private final LoginPresenter presenter;
    private final LoginAttemptObserver observer;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface LoginAttemptObserver {
        void loginAttempted(LoginResponse loginResponse);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter from whom this task should get the updated login info.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public LoginAttemptTask(LoginPresenter presenter, LoginAttemptObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected LoginResponse doInBackground(LoginRequest... loginRequests) {
        LoginResponse response = null;
        try {
            response = presenter.checkLogin(loginRequests[0]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
        // loadImages(response) is not necessary because the user image is
        // already asynchronously loaded in the MainActivity directly.
        return response;
    }

    /**
     * Notifies the observer (on the UI thread) when the task completes
     *
     * @param loginResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(LoginResponse loginResponse) {
        if (observer != null) {
            observer.loginAttempted(loginResponse);
        }
    }



}