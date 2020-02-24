package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.services.LoginService;
import edu.byu.cs.tweeter.net.request.LoginRequest;
import edu.byu.cs.tweeter.net.response.LoginResponse;

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
        return LoginService.getInstance().checkLogin(request);
    }
}
