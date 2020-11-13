package byu.edu.cs.tweeter.server.service;

import java.util.UUID;

import byu.edu.cs.tweeter.server.dao.AuthTokenDAO;
import byu.edu.cs.tweeter.server.dao.UserDAO;
import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.model.domain.service.LoginService;
import byu.edu.cs.tweeter.shared.request.LoginRequest;
import byu.edu.cs.tweeter.shared.response.LoginResponse;

public class LoginServiceImpl implements LoginService {
    @Override
    public LoginResponse checkLogin(LoginRequest request) {
        // TODO: create an authtoken and add it to the table if credentials are correct
        boolean correctAliasPassword = getUserDAO().validateLogin(request.getAlias(), request.getPassword());
        if (!correctAliasPassword) {
            return new LoginResponse("Failure! Incorrect login credentials..");
        }
        String newAuthToken = UUID.randomUUID().toString();
        getAuthTokenDAO().createAuthToken(new AuthToken(newAuthToken), request.getAlias());

        // The following line only works without a hardcoded TestUser
        // User user = getUserDAO().findUserByAlias(request.getAlias());
        // We use the following line for now:
        User user;
        if (request.getAlias().equals("@TestUser")) {
            user = new User("Test", "User",
                    "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        }
        else {
            user = new User("Registered", "User", request.getAlias(), "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        }

        return new LoginResponse("Success! Logged in..", user, new AuthToken(newAuthToken));
    }

    UserDAO getUserDAO() {return new UserDAO();}
    AuthTokenDAO getAuthTokenDAO() {return new AuthTokenDAO();}
}
