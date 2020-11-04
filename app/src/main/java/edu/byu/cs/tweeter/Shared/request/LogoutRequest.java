package edu.byu.cs.tweeter.Shared.request;

import edu.byu.cs.tweeter.Shared.domain.AuthToken;

public class LogoutRequest {
    private AuthToken authToken;

    public LogoutRequest(AuthToken authToken) {
        this.authToken = authToken;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }
}
