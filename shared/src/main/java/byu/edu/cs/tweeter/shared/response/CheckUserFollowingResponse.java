package byu.edu.cs.tweeter.shared.response;

public class CheckUserFollowingResponse extends Response {
    private boolean userFollowing;

    public CheckUserFollowingResponse(boolean userFollowing) {
        super(true);
        this.userFollowing = userFollowing;
    }
    public CheckUserFollowingResponse(String message) {
        super(false, message);
    }
    public boolean isUserFollowing() {
        return userFollowing;
    }

    public void setUserFollowing(boolean userFollowing) {
        this.userFollowing = userFollowing;
    }
    public CheckUserFollowingResponse() {}
}
