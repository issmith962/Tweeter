package byu.edu.cs.tweeter.server.dao.DAOHelpers;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

import byu.edu.cs.tweeter.shared.request.UpdateFeedRequest;
import byu.edu.cs.tweeter.shared.utils.JsonSerializer;

public class UpdateFeedSQSManager {
    private static String queueUrl = "https://sqs.us-west-2.amazonaws.com/479560496061/UpdateFeedSQSQueue.fifo";
    private static String messageGroupId = "update_feeds_of_followers";

    private static AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

    public static void updateFeed(UpdateFeedRequest request) {
        String requestAsJson = JsonSerializer.serialize(request);
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(requestAsJson)
                .withMessageGroupId(messageGroupId);
        SendMessageResult sendMessageResult = sqs.sendMessage(sendMessageRequest);
    }

}
