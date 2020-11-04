package edu.byu.cs.tweeter.Shared.response;

public class FollowUserResponse {
    private String message;

    public FollowUserResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
