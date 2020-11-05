package edu.byu.cs.tweeter.Client.model.services;

import android.os.Build;

import androidx.annotation.RequiresApi;

import edu.byu.cs.tweeter.Client.net.ServerFacade;
import byu.edu.cs.tweeter.shared.domain.AuthToken;
import byu.edu.cs.tweeter.shared.domain.User;
import byu.edu.cs.tweeter.shared.request.FolloweeCountRequest;
import byu.edu.cs.tweeter.shared.request.FollowerCountRequest;
import byu.edu.cs.tweeter.shared.request.LoginRequest;
import byu.edu.cs.tweeter.shared.request.StartUpRequest;
import byu.edu.cs.tweeter.shared.response.FolloweeCountResponse;
import byu.edu.cs.tweeter.shared.response.FollowerCountResponse;
import byu.edu.cs.tweeter.shared.response.LoginResponse;
import byu.edu.cs.tweeter.shared.response.StartUpResponse;

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
    private AuthToken currentAuthToken;

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
        currentAuthToken = null;
    }

    /**
     * Checks the login attempt. If successful, sets the current User to the response's data.
     * If unsuccessful, current user remains null.
     * @param request the LoginRequest containing the alias and password to be checked.
     */
    public LoginResponse checkLogin(LoginRequest request) {
        LoginResponse response = serverFacade.checkLogin(request);
        if (!(response.getAuthToken() == null)) {
            currentAuthToken = response.getAuthToken();
//            if (response.getImageURL() == null) {
//                setCurrentUser(new User(response.getFirstName(), response.getLastName(),
//                        response.getAlias(), response.getImageUri()));
//            }
//            else {
            setCurrentUser(new User(response.getFirstName(), response.getLastName(),
                    response.getAlias(), response.getImageURL()));
//            }
        }
        return response;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public StartUpResponse startUp(StartUpRequest request) {
        return serverFacade.startUp(request);
    }

    public AuthToken getCurrentAuthToken() {
        return currentAuthToken;
    }

    public void setCurrentAuthToken(AuthToken currentAuthToken) {
        this.currentAuthToken = currentAuthToken;
    }

    /**
     * Returns the currently logged in user.
     *
     * @return the user.
     */


    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public FollowerCountResponse getFollowerCount(FollowerCountRequest request) {
        return serverFacade.getFollowerCount(request);
    }
    public FolloweeCountResponse getFolloweeCount(FolloweeCountRequest request) {
        return serverFacade.getFolloweeCount(request);
    }
}