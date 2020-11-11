package byu.edu.cs.tweeter.server.dao;

import java.util.List;

import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.request.RegisterRequest;
import byu.edu.cs.tweeter.shared.response.RegisterResponse;

public class UserDAO {
    public RegisterResponse register(RegisterRequest request) {
        // TODO: check if alias is already use
        //  then add the user to the table
        return new RegisterResponse(new User(request.getName().split(" ")[0], request.getName().split(" ")[1],
                request.getAlias(), "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png"),
                request.getPassword());
    }
    public User findUserByAlias(String alias) {
        return new User("Found", "Me",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
    }

    public boolean validateLogin(String alias, String password) {
        // TODO: check login credentials again User table, return true if they match
        return true;
    }
}
