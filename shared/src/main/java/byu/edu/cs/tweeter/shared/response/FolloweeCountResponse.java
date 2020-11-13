package byu.edu.cs.tweeter.shared.response;

public class FolloweeCountResponse extends Response {
    private int followeeCount;

    public FolloweeCountResponse(int followeeCount) {
        super(true);
        this.followeeCount = followeeCount;
    }

    public void setFolloweeCount(int followeeCount) {
        this.followeeCount = followeeCount;
    }

    public FolloweeCountResponse(String message) {
        super(false, message);
    }

    public int getFolloweeCount() {
        return followeeCount;
    }

    public FolloweeCountResponse() {}

}
