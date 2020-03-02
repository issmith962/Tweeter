package edu.byu.cs.tweeter.net.response;

import edu.byu.cs.tweeter.model.domain.User;

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
