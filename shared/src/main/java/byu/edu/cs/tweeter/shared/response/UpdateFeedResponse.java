package byu.edu.cs.tweeter.shared.response;

public class UpdateFeedResponse extends Response {
    public UpdateFeedResponse(boolean success) {
        super(success);
    }

    public UpdateFeedResponse(boolean success, String message) {
        super(success, message);
    }

    public UpdateFeedResponse() {}



}
