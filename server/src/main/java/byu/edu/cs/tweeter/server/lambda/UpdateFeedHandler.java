package byu.edu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import byu.edu.cs.tweeter.server.service.FeedServiceImpl;
import byu.edu.cs.tweeter.shared.request.UpdateFeedRequest;
import byu.edu.cs.tweeter.shared.response.UpdateFeedResponse;

public class UpdateFeedHandler implements RequestHandler<UpdateFeedRequest, UpdateFeedResponse> {
    @Override
    public UpdateFeedResponse handleRequest(UpdateFeedRequest input, Context context) {
        FeedServiceImpl service = new FeedServiceImpl();
        UpdateFeedResponse response = service.updateFeed(input);
        return response;
    }
}
