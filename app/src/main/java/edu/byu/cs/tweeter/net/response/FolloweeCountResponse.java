package edu.byu.cs.tweeter.net.response;

public class FolloweeCountResponse{
    private int followeeCount;

    public FolloweeCountResponse(int followeeCount) {
        this.followeeCount = followeeCount;
    }

    public int getFolloweeCount() {
        return followeeCount;
    }

}
