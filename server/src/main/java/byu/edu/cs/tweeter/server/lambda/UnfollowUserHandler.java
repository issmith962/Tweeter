package byu.edu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import byu.edu.cs.tweeter.server.service.FollowActionServiceImpl;
import byu.edu.cs.tweeter.shared.request.UnfollowUserRequest;
import byu.edu.cs.tweeter.shared.response.UnfollowUserResponse;

public class UnfollowUserHandler implements RequestHandler<UnfollowUserRequest, UnfollowUserResponse> {

    @Override
    public UnfollowUserResponse handleRequest(UnfollowUserRequest input, Context context) {
        FollowActionServiceImpl service = new FollowActionServiceImpl();
        return service.unfollowUser(input);
    }
}
