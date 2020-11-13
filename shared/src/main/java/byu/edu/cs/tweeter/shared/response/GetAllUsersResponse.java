package byu.edu.cs.tweeter.shared.response;

import java.util.List;
import java.util.Objects;

import byu.edu.cs.tweeter.shared.model.domain.User;

public class GetAllUsersResponse extends Response{
    private List<User> allUsers;

    public GetAllUsersResponse(List<User> allUsers) {
        super(true);
        this.allUsers = allUsers;
    }

    public GetAllUsersResponse(String message) {
        super(false, message);
    }
    public List<User> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(List<User> allUsers) {
        this.allUsers = allUsers;
    }

    public GetAllUsersResponse() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GetAllUsersResponse that = (GetAllUsersResponse) o;
        return Objects.equals(allUsers, that.allUsers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), allUsers);
    }
}
