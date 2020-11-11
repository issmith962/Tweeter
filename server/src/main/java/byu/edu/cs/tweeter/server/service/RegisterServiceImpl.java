package byu.edu.cs.tweeter.server.service;

import byu.edu.cs.tweeter.server.dao.UserDAO;
import byu.edu.cs.tweeter.shared.model.domain.service.RegisterService;
import byu.edu.cs.tweeter.shared.request.RegisterRequest;
import byu.edu.cs.tweeter.shared.response.RegisterResponse;

public class RegisterServiceImpl implements RegisterService {
    @Override
    public RegisterResponse registerUser(RegisterRequest request) {
        String[] names = request.getName().split(" ");
        if (names.length != 2) {
            return new RegisterResponse("Failure: Need first and last name to register.");
        }
        else {
            return getUserDAO().register(request);
        }
    }

    UserDAO getUserDAO() {return new UserDAO();}
}
