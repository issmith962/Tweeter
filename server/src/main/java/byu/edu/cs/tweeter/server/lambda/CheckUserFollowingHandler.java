package byu.edu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import byu.edu.cs.tweeter.server.service.CheckUserFollowingServiceImpl;
import byu.edu.cs.tweeter.shared.request.CheckUserFollowingRequest;
import byu.edu.cs.tweeter.shared.response.CheckUserFollowingResponse;

public class CheckUserFollowingHandler implements RequestHandler<CheckUserFollowingRequest, CheckUserFollowingResponse> {

    @Override
    public CheckUserFollowingResponse handleRequest(CheckUserFollowingRequest input, Context context) {
        CheckUserFollowingServiceImpl service = new CheckUserFollowingServiceImpl();
        return service.isUserFollowing(input);
    }
}
