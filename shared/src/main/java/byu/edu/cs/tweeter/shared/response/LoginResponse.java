package byu.edu.cs.tweeter.shared.response;

import java.util.Objects;

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

    public LoginResponse() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LoginResponse that = (LoginResponse) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(authToken, that.authToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, authToken);
    }
}