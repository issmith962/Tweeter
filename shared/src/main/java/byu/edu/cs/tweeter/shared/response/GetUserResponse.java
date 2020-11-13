package byu.edu.cs.tweeter.shared.response;

import java.util.Objects;

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

    public void setUser(User user) {
        this.user = user;
    }

    public GetUserResponse() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GetUserResponse that = (GetUserResponse) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user);
    }
}
