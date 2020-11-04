package edu.byu.cs.tweeter.Shared.response;

import edu.byu.cs.tweeter.Shared.domain.User;

public class GetUserResponse {
    private User user;

    public GetUserResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
