package byu.edu.cs.tweeter.shared.model.domain;

import java.io.Serializable;
import java.util.Objects;

public class AuthToken implements Comparable<AuthToken>, Serializable {
    private String authToken;

    public AuthToken() {}
    public AuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken1 = (AuthToken) o;
        return Objects.equals(authToken, authToken1.authToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authToken);
    }

    @Override
    public String toString() {
        return "AuthToken{" +
                "authToken='" + authToken + '\'' +
                '}';
    }

    @Override
    public int compareTo(AuthToken authToken) {
        return this.getAuthToken().compareTo(authToken.getAuthToken());
    }
}
