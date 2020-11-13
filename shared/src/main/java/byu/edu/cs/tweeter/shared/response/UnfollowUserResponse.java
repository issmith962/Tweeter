package byu.edu.cs.tweeter.shared.response;

public class UnfollowUserResponse extends Response {

    public UnfollowUserResponse(boolean success) {
        super(success);
    }

    public UnfollowUserResponse(boolean success, String message) {
        super(success, message);
    }

    public UnfollowUserResponse() {}
}
