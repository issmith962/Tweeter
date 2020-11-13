package byu.edu.cs.tweeter.shared.response;

import byu.edu.cs.tweeter.shared.model.domain.User;

public class RegisterResponse extends Response{
    private User newUser;
    private String password;

    public RegisterResponse(User newUser, String password) {
        super(true);
        this.newUser = newUser;
        this.password = password;
    }
    public RegisterResponse(String message) {
        super(false, message);
    }

    public User getNewUser() {
        return newUser;
    }
    public String getPassword() {
        return password;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
