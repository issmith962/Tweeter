package byu.edu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import byu.edu.cs.tweeter.server.service.FeedServiceImpl;
import byu.edu.cs.tweeter.shared.request.FeedRequest;
import byu.edu.cs.tweeter.shared.response.FeedResponse;

public class FeedHandler implements RequestHandler<FeedRequest, FeedResponse> {

    @Override
    public FeedResponse handleRequest(FeedRequest input, Context context) {
        FeedServiceImpl service = new FeedServiceImpl();
        FeedResponse feed = service.getFeed(input);
        System.out.println(feed.hasMorePages());
        return feed;
    }
}
