package byu.edu.cs.tweeter.shared.response;

public class UnfollowUserResponse {
    private String message;

    public UnfollowUserResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

