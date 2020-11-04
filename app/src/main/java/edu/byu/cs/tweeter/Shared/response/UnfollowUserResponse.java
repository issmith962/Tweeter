package edu.byu.cs.tweeter.Shared.response;

public class UnfollowUserResponse {
    private String message;

    public UnfollowUserResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

