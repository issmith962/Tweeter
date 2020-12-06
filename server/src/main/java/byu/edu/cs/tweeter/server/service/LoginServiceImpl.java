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
        else if (request.getAlias().equals("")) {
            return new LoginResponse("Bad Request: username cannot be empty..");
        }
        else if (request.getPassword() == null) {
            return new LoginResponse("Bad Request: no password given..");
        }
        else if (request.getPassword().equals("")) {
            return new LoginResponse("Bad Request: password cannot be empty..");
        }
        else if (request.getAlias().charAt(0) == '@') {
            request.setAlias(request.getAlias().substring(1));
        }

        if (request.getAlias().contains("@")) {
            return new LoginResponse("Bad Request: cannot have a '@' in alias..");
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

        User user = getUserDAO().findUserByAlias(request.getAlias());
        if (user == null) {
            return new LoginResponse("Credentials checked out but user data is incomplete");
        }
        else {
            return new LoginResponse("Success! Logged in..", user, new AuthToken(newAuthToken));
        }
    }

    public UserDAO getUserDAO() {return new UserDAO();}
    public AuthTokenDAO getAuthTokenDAO() {return new AuthTokenDAO();}
}
