package byu.edu.cs.tweeter.shared.response;

import byu.edu.cs.tweeter.shared.model.domain.User;

public class RegisterResponse {
    private User newUser;
    private String password;

    public RegisterResponse(User newUser, String password) {
        this.newUser = newUser;
        this.password = password;
    }

    public User getNewUser() {
        return newUser;
    }
    public String getPassword() {
        return password;
    }
}
