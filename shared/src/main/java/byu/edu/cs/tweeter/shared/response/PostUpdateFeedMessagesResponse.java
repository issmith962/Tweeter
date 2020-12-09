package byu.edu.cs.tweeter.shared.response;

public class PostUpdateFeedMessagesResponse extends Response {
    public PostUpdateFeedMessagesResponse(boolean success) {
        super(success);
    }

    public PostUpdateFeedMessagesResponse(boolean success, String message) {
        super(success, message);
    }

    public PostUpdateFeedMessagesResponse() {}
}
