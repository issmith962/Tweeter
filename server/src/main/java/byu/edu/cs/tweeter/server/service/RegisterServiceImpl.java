package byu.edu.cs.tweeter.server.service;

import byu.edu.cs.tweeter.server.dao.UserDAO;
import byu.edu.cs.tweeter.shared.model.domain.service.RegisterService;
import byu.edu.cs.tweeter.shared.request.RegisterRequest;
import byu.edu.cs.tweeter.shared.response.RegisterResponse;

public class RegisterServiceImpl implements RegisterService {
    @Override
    public RegisterResponse registerUser(RegisterRequest request) {
        if ((request.getAlias() == null) || (request.getName() == null) || (request.getPassword() == null)) {
            return new RegisterResponse("Bad Request: one or more registration fields missing");
        }
        if ((request.getAlias().equals("")) || (request.getName().equals("")) || (request.getPassword().equals(""))) {
            return new RegisterResponse("Bad Request: registration fields cannot be empty");
        }

        String[] names = request.getName().split(" ");
        if (names.length != 2) {
            return new RegisterResponse("Failure: Need first and last name to register.");
        }
        else {
            return getUserDAO().register(request);
        }
    }

    public UserDAO getUserDAO() {return new UserDAO();}
}
