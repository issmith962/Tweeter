package byu.edu.cs.tweeter.shared.response;

import java.util.Objects;

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

    public RegisterResponse() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RegisterResponse that = (RegisterResponse) o;
        return Objects.equals(newUser, that.newUser) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), newUser, password);
    }
}
