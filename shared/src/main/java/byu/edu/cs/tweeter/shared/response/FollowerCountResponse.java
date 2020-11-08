package byu.edu.cs.tweeter.shared.response;

public class FollowerCountResponse extends Response{
    private int followerCount;

    public FollowerCountResponse(int followerCount) {
        super(true);
        this.followerCount = followerCount;
    }

    public FollowerCountResponse(String message) {
        super(false, message);
    }

    public int getFollowerCount() {
        return followerCount;
    }

}
