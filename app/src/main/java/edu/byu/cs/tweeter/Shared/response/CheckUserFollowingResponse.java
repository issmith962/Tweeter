package edu.byu.cs.tweeter.Shared.response;

public class CheckUserFollowingResponse {
    private boolean userFollowing;

    public CheckUserFollowingResponse(boolean userFollowing) {
        this.userFollowing = userFollowing;
    }

    public boolean isUserFollowing() {
        return userFollowing;
    }
}
