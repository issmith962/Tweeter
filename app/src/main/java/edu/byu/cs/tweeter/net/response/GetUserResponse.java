package edu.byu.cs.tweeter.net.response;

import edu.byu.cs.tweeter.model.domain.User;

public class GetUserResponse {
    private User user;

    public GetUserResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
