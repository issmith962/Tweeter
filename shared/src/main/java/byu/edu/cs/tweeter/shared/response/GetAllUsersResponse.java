package byu.edu.cs.tweeter.shared.response;

import java.util.List;

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
}
