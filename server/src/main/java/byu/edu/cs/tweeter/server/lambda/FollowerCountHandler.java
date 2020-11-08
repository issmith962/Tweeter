package byu.edu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import byu.edu.cs.tweeter.server.service.LoginServiceImpl;
import byu.edu.cs.tweeter.shared.request.FollowerCountRequest;
import byu.edu.cs.tweeter.shared.response.FollowerCountResponse;

public class FollowerCountHandler implements RequestHandler<FollowerCountRequest, FollowerCountResponse> {

    @Override
    public FollowerCountResponse handleRequest(FollowerCountRequest input, Context context) {
        LoginServiceImpl service = new LoginServiceImpl();
        return service.getFollowerCount(input);
    }
}
