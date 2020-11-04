package edu.byu.cs.tweeter.Shared.request;

public class GetUserRequest {
    private String alias;

    public GetUserRequest(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }
}
