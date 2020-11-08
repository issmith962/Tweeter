package byu.edu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import byu.edu.cs.tweeter.server.service.FollowActionServiceImpl;
import byu.edu.cs.tweeter.shared.request.FollowUserRequest;
import byu.edu.cs.tweeter.shared.response.FollowUserResponse;

public class FollowUserHandler implements RequestHandler<FollowUserRequest, FollowUserResponse> {

    @Override
    public FollowUserResponse handleRequest(FollowUserRequest input, Context context) {
        FollowActionServiceImpl service = new FollowActionServiceImpl();
        return service.followUser(input);
    }
}
