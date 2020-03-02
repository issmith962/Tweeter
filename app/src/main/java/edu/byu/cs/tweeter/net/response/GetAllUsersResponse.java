package edu.byu.cs.tweeter.net.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public class GetAllUsersResponse {
    private List<User> allUsers;

    public GetAllUsersResponse(List<User> allUsers) {
        this.allUsers = allUsers;
    }
    public List<User> getAllUsers() {
        return allUsers;
    }
}
