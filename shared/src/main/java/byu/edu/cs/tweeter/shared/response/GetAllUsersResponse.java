package byu.edu.cs.tweeter.shared.response;

import java.util.List;

import byu.edu.cs.tweeter.shared.model.domain.User;

public class GetAllUsersResponse {
    private List<User> allUsers;

    public GetAllUsersResponse(List<User> allUsers) {
        this.allUsers = allUsers;
    }
    public List<User> getAllUsers() {
        return allUsers;
    }
}
