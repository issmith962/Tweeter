package byu.edu.cs.tweeter.server.service;

import byu.edu.cs.tweeter.server.dao.UserDAO;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.model.domain.service.GetUserService;
import byu.edu.cs.tweeter.shared.request.GetUserRequest;
import byu.edu.cs.tweeter.shared.response.GetUserResponse;

public class GetUserServiceImpl implements GetUserService {
    @Override
    public GetUserResponse getUser(GetUserRequest request) {
        if (request.getAlias() == null) {
            return new GetUserResponse("Bad Request: no user requested..");
        }
        if (request.getAlias().equals("")) {
            return new GetUserResponse("Bad Request: alias cannot be empty..");
        }
        User user = getUserDAO().findUserByAlias(request.getAlias());
        if (user == null) {
            return new GetUserResponse("Bad Request: user doesn't exist");
        }
        else {
            return new GetUserResponse(user);
        }
    }
    public UserDAO getUserDAO() {
        return new UserDAO();
    }
}
