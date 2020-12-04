package byu.edu.cs.tweeter.server.service;

import java.util.UUID;

import byu.edu.cs.tweeter.server.dao.AuthTokenDAO;
import byu.edu.cs.tweeter.server.dao.UserDAO;
import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.model.domain.service.LoginService;
import byu.edu.cs.tweeter.shared.net.DataAccessException;
import byu.edu.cs.tweeter.shared.request.LoginRequest;
import byu.edu.cs.tweeter.shared.response.LoginResponse;

public class LoginServiceImpl implements LoginService {
    @Override
    public LoginResponse checkLogin(LoginRequest request) {
        if (request.getAlias() == null) {
            return new LoginResponse("Bad Request: no username given..");
        }
        if (request.getAlias().equals("")) {
            return new LoginResponse("Bad Request: username cannot be empty..");
        }
        if (request.getPassword() == null) {
            return new LoginResponse("Bad Request: no password given..");
        }
        if (request.getPassword().equals("")) {
            return new LoginResponse("Bad Request: password cannot be empty..");
        }

        // TODO: create an authtoken and add it to the table if credentials are correct
        boolean correctAliasPassword = getUserDAO().validateLogin(request.getAlias(), request.getPassword());
        if (!correctAliasPassword) {
            return new LoginResponse("Failure! Incorrect login credentials..");
        }
        String newAuthToken = UUID.randomUUID().toString();

        try {
            long twoHoursInSeconds = 7200;
            long exptime = System.currentTimeMillis()/1000 + twoHoursInSeconds;
            getAuthTokenDAO().createAuthToken(new AuthToken(newAuthToken), exptime, request.getAlias());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

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

    public UserDAO getUserDAO() {return new UserDAO();}
    public AuthTokenDAO getAuthTokenDAO() {return new AuthTokenDAO();}
}
