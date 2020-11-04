package byu.edu.cs.tweeter.shared.response;

public class FollowUserResponse {
    private String message;

    public FollowUserResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
