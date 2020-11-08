package byu.edu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import byu.edu.cs.tweeter.server.service.LoginServiceImpl;
import byu.edu.cs.tweeter.shared.request.FolloweeCountRequest;
import byu.edu.cs.tweeter.shared.response.FolloweeCountResponse;

public class FolloweeCountHandler implements RequestHandler<FolloweeCountRequest, FolloweeCountResponse> {

    @Override
    public FolloweeCountResponse handleRequest(FolloweeCountRequest input, Context context) {
        LoginServiceImpl service = new LoginServiceImpl();
        return service.getFolloweeCount(input);
    }
}
