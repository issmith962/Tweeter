package byu.edu.cs.tweeter.server.dao;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

import byu.edu.cs.tweeter.shared.request.PostUpdateFeedMessagesRequest;
import byu.edu.cs.tweeter.shared.utils.JsonSerializer;

public class PostUpdateFeedMessagesSQSManager {
    private static String queueUrl = "https://sqs.us-west-2.amazonaws.com/479560496061/PostUpdateFeedMessagesSQSQueue.fifo";
    private static String messageGroupId = "post_status_feed_update_message";
    private static AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

    public static void sendUpdateFeedMessage(PostUpdateFeedMessagesRequest request) {
        String requestAsJson = JsonSerializer.serialize(request);
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(requestAsJson)
                .withMessageGroupId(messageGroupId);
        SendMessageResult sendMessageResult = sqs.sendMessage(sendMessageRequest);
    }


}
