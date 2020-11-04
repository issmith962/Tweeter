package edu.byu.cs.tweeter.Shared.response;

public class LogoutResponse extends Response {
    LogoutResponse(boolean success) {
        super(success);
    }

    public LogoutResponse(boolean success, String message) {
        super(success, message);
    }
}
