package byu.edu.cs.tweeter.shared.response;

public class FolloweeCountResponse{
    private int followeeCount;

    public FolloweeCountResponse(int followeeCount) {
        this.followeeCount = followeeCount;
    }

    public int getFolloweeCount() {
        return followeeCount;
    }

}
