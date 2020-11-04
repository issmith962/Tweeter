package byu.edu.cs.tweeter.shared.response;

public class StartUpResponse {
    private String message;

    public StartUpResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
