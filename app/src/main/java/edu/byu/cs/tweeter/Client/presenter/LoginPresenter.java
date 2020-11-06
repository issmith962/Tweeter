package edu.byu.cs.tweeter.Client.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import byu.edu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.Client.model.services.LoginServiceProxy;
import edu.byu.cs.tweeter.Client.model.services.RegisterServiceProxy;
import byu.edu.cs.tweeter.shared.request.LoginRequest;
import byu.edu.cs.tweeter.shared.request.RegisterRequest;
import byu.edu.cs.tweeter.shared.request.StartUpRequest;
import byu.edu.cs.tweeter.shared.response.LoginResponse;
import byu.edu.cs.tweeter.shared.response.RegisterResponse;
import byu.edu.cs.tweeter.shared.response.StartUpResponse;

/**
 * The presenter for the "login" functionality of the application.
 */
public class LoginPresenter extends Presenter {
    private final View view;

    /**
     * The interface by which this presenter communicates with its view.
     */
    public interface View {
        // if needed, specify methods here that will
        // be called on the view in response to model updates
    }

    /**
     * Creates an instance
     *
     * @param view the view for which this class is the presenter.
     */
    public LoginPresenter(View view) {
        this.view = view;
    }

    /**
     * Checks a login request, changes the current user if necessary.
     *
     * @param request the login request containing alias and password.
     * @return the updated current user if the login worked, or the previous one if not.
     */
    public LoginResponse checkLogin(LoginRequest request) {
        LoginResponse response = (new LoginServiceProxy()).checkLogin(request);
        if (!(response.getAuthToken() == null)) {
            setCurrentAuthToken(response.getAuthToken());
            setCurrentUser(new User(response.getFirstName(), response.getLastName(),
                    response.getAlias(), response.getImageURL()));
        }
        return response;
    }

    public RegisterResponse registerUser(RegisterRequest request) {
        return (new RegisterServiceProxy()).registerUser(request);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public StartUpResponse startUp(StartUpRequest request) {
        return (new LoginServiceProxy()).startUp(request);
    }
}