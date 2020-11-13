package byu.edu.cs.tweeter.shared.response;

import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.User;

public class LoginResponse extends Response {
    private User user;
    private AuthToken authToken;

    public LoginResponse(String message, User user, AuthToken authToken) {
        super(true, message);
        this.user = user;
        this.authToken = authToken;
    }

    public LoginResponse(String message) {
        super(false, message);
    }

    public AuthToken getAuthToken() {
        return authToken;
    }
    public User getUser() {return user;}

    public void setUser(User user) {
        this.user = user;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}