package edu.byu.cs.tweeter.Shared.response;

public class FolloweeCountResponse{
    private int followeeCount;

    public FolloweeCountResponse(int followeeCount) {
        this.followeeCount = followeeCount;
    }

    public int getFolloweeCount() {
        return followeeCount;
    }

}
