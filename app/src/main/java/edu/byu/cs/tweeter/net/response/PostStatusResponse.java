package edu.byu.cs.tweeter.net.response;

public class PostStatusResponse {
    String message;

    public PostStatusResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
