package byu.edu.cs.tweeter.shared.response;

import byu.edu.cs.tweeter.shared.model.domain.User;

public class GetUserResponse {
    private User user;

    public GetUserResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
