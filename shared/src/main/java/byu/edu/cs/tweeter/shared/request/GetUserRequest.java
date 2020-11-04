package byu.edu.cs.tweeter.shared.request;

public class GetUserRequest {
    private String alias;

    public GetUserRequest(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }
}
