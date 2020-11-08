package byu.edu.cs.tweeter.shared.response;

import byu.edu.cs.tweeter.shared.model.domain.User;

public class GetUserResponse extends Response {
    private User user;

    public GetUserResponse(User user) {
        super(true);
        this.user = user;
    }

    public GetUserResponse(String message) {
        super(false,message);
    }

    public User getUser() {
        return user;
    }
}
