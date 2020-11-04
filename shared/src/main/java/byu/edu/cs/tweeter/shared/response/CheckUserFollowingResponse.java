package byu.edu.cs.tweeter.shared.response;

public class CheckUserFollowingResponse {
    private boolean userFollowing;

    public CheckUserFollowingResponse(boolean userFollowing) {
        this.userFollowing = userFollowing;
    }

    public boolean isUserFollowing() {
        return userFollowing;
    }
}
