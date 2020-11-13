package byu.edu.cs.tweeter.shared.response;

public class FollowUserResponse extends Response {
    public FollowUserResponse(boolean success) {
        super(success);
    }

    public FollowUserResponse(boolean success, String message) {
        super(success, message);
    }

    public FollowUserResponse() {}
}
