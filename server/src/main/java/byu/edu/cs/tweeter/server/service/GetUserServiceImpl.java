package byu.edu.cs.tweeter.server.service;

import byu.edu.cs.tweeter.server.dao.UserDAO;
import byu.edu.cs.tweeter.shared.model.domain.service.GetUserService;
import byu.edu.cs.tweeter.shared.request.GetUserRequest;
import byu.edu.cs.tweeter.shared.response.GetUserResponse;

public class GetUserServiceImpl implements GetUserService {
    @Override
    public GetUserResponse getUser(GetUserRequest request) {
        return new GetUserResponse(getUserDAO().findUserByAlias(request.getAlias()));
    }
    UserDAO getUserDAO() {
        return new UserDAO();
    }
}
