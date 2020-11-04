package byu.edu.cs.tweeter.shared.response;

public class LogoutResponse extends Response {
    LogoutResponse(boolean success) {
        super(success);
    }

    public LogoutResponse(boolean success, String message) {
        super(success, message);
    }
}
