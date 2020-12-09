package byu.edu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;

import byu.edu.cs.tweeter.server.service.FeedServiceImpl;
import byu.edu.cs.tweeter.shared.request.UpdateFeedRequest;
import byu.edu.cs.tweeter.shared.utils.JsonSerializer;

public class UpdateFeedHandler implements RequestHandler<SQSEvent, Void> {
    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        FeedServiceImpl service = new FeedServiceImpl();
        for (SQSMessage msg : event.getRecords()) {
            UpdateFeedRequest request = JsonSerializer.deserialize(msg.getBody(), UpdateFeedRequest.class);
            FeedServiceImpl feedService = new FeedServiceImpl();
            feedService.updateFeed(request);
        }
        return null;
    }
}
