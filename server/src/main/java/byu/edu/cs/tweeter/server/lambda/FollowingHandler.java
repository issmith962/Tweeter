package byu.edu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import byu.edu.cs.tweeter.server.service.FollowerServiceImpl;
import byu.edu.cs.tweeter.server.service.FollowingServiceImpl;
import byu.edu.cs.tweeter.shared.request.FollowingRequest;
import byu.edu.cs.tweeter.shared.response.FollowingResponse;

public class FollowingHandler implements RequestHandler<FollowingRequest, FollowingResponse> {

    @Override
    public FollowingResponse handleRequest(FollowingRequest input, Context context) {
        FollowingServiceImpl service = new FollowingServiceImpl();
        return service.getFollowees(input);
    }
}
