package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.LoginRequest;
import edu.byu.cs.tweeter.net.response.LoginResponse;

/**
 * Contains the business logic for login and sign up.
 */
public class LoginService {

    /**
     * The singleton instance.
     */
    private static LoginService instance;

    private final ServerFacade serverFacade;
    /**
     * The logged in user.
     */
    private User currentUser;

    /**
     * Return the singleton instance of this class.
     *
     * @return the instance.
     */
    public static LoginService getInstance() {
        if(instance == null) {
            instance = new LoginService();
        }

        return instance;
    }

    /**
     * A private constructor created to ensure that this class is a singleton (i.e. that it
     * cannot be instantiated by external classes).
     */
    private LoginService() {
        serverFacade = new ServerFacade();
        currentUser = null;
    }

    /**
     * Checks the login attempt. If successful, sets the current User to the response's data.
     * If unsuccessful, current user remains null.
     * @param request the LoginRequest containing the alias and password to be checked.
     */
    public LoginResponse checkLogin(LoginRequest request) {
        LoginResponse response = serverFacade.checkLogin(request);
        if (!(response.getAuthToken() == null)) {
            setCurrentUser(new User(response.getFirstName(), response.getLastName(),
                    response.getAlias(), response.getImageURL()));
        }
        return response;
    }

    /**
     * Returns the currently logged in user.
     *
     * @return the user.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    private void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}