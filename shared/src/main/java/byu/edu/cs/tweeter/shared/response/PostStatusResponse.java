package byu.edu.cs.tweeter.shared.response;

public class PostStatusResponse {
    String message;

    public PostStatusResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
