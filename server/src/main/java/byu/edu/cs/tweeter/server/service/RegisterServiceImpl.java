package byu.edu.cs.tweeter.server.service;

import byu.edu.cs.tweeter.server.dao.UserDAO;
import byu.edu.cs.tweeter.server.encryption.SaltedSHAHashing;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.model.domain.service.RegisterService;
import byu.edu.cs.tweeter.shared.net.DataAccessException;
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

        if (request.getAlias().charAt(0) == '@') {
            request.setAlias(request.getAlias().substring(1));
        }

        if (request.getAlias().contains("@")) {
            return new RegisterResponse("Bad Request: cannot have a '@' in alias..");
        }

        String[] names = request.getName().split(" ");
        if (names.length != 2) {
            return new RegisterResponse("Failure: Need first and last name to register.");
        }
        else {
            try {
                // check if alias is taken
                if (getUserDAO().isAliasTaken(request.getAlias())) {
                    return new RegisterResponse("Alias already taken!!");
                }
                // store profile picture
                String newProfPicUrl = getUserDAO().storeProfPic(request.getAlias(), request.getImageData());

                // hash password
                String salt = SaltedSHAHashing.getSalt();
                String securePassword = SaltedSHAHashing.getSecurePassword(request.getPassword(), salt);

                // add user to table
                String firstName = request.getName().split(" ")[0];
                String lastName = request.getName().split(" ")[1];

                getUserDAO().registerNewUser(request.getAlias(), firstName,
                        lastName, securePassword, salt, newProfPicUrl);
                User newUser = new User(firstName, lastName, request.getAlias(), newProfPicUrl);
                return new RegisterResponse(newUser, request.getPassword());
            } catch (DataAccessException e) {
                e.printStackTrace();
                return new RegisterResponse(e.getMessage());
            }
        }

    }

    public UserDAO getUserDAO() {return new UserDAO();}
}
