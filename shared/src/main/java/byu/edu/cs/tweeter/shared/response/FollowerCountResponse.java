package byu.edu.cs.tweeter.shared.response;

import java.util.Objects;

public class FollowerCountResponse extends Response{
    private int followerCount;

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

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

    public FollowerCountResponse() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FollowerCountResponse that = (FollowerCountResponse) o;
        return followerCount == that.followerCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), followerCount);
    }
}
