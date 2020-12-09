package byu.edu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;

import byu.edu.cs.tweeter.server.service.PostStatusServiceImpl;
import byu.edu.cs.tweeter.shared.request.PostUpdateFeedMessagesRequest;
import byu.edu.cs.tweeter.shared.utils.JsonSerializer;

public class PostUpdateFeedMessagesHandler implements RequestHandler<SQSEvent, Void> {
    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        PostStatusServiceImpl service = new PostStatusServiceImpl();
        for (SQSMessage msg : event.getRecords()) {
            PostUpdateFeedMessagesRequest request = JsonSerializer.deserialize(msg.getBody(), PostUpdateFeedMessagesRequest.class);
            PostStatusServiceImpl postStatusService = new PostStatusServiceImpl();
            postStatusService.sendUpdateFeedMessage(request);
        }
        return null;
    }
}
