package byu.edu.cs.tweeter.shared.response;

public class FollowerCountResponse{
    private int followerCount;

    public FollowerCountResponse(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getFollowerCount() {
        return followerCount;
    }

}
