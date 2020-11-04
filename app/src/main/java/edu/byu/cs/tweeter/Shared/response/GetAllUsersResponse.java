package edu.byu.cs.tweeter.Shared.response;

import java.util.List;

import edu.byu.cs.tweeter.Shared.domain.User;

public class GetAllUsersResponse {
    private List<User> allUsers;

    public GetAllUsersResponse(List<User> allUsers) {
        this.allUsers = allUsers;
    }
    public List<User> getAllUsers() {
        return allUsers;
    }
}
