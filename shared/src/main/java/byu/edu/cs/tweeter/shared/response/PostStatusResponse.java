package byu.edu.cs.tweeter.shared.response;

public class PostStatusResponse extends Response {

    public PostStatusResponse(boolean success) {
        super(success);
    }

    public PostStatusResponse(boolean success, String message) {
        super(success, message);
    }
}
