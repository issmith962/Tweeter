package edu.byu.cs.tweeter.net.response;

public class CheckUserFollowingResponse {
    private boolean userFollowing;

    public CheckUserFollowingResponse(boolean userFollowing) {
        this.userFollowing = userFollowing;
    }

    public boolean isUserFollowing() {
        return userFollowing;
    }
}
