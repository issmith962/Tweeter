package byu.edu.cs.tweeter.shared.response;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FolloweeCountResponse response = (FolloweeCountResponse) o;
        return followeeCount == response.followeeCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), followeeCount);
    }
}
