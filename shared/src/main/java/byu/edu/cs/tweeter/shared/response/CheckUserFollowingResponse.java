package byu.edu.cs.tweeter.shared.response;

import java.util.Objects;

public class CheckUserFollowingResponse extends Response {
    private boolean userFollowing;

    public CheckUserFollowingResponse(boolean userFollowing) {
        super(true);
        this.userFollowing = userFollowing;
    }
    public CheckUserFollowingResponse(String message) {
        super(false, message);
    }
    public boolean isUserFollowing() {
        return userFollowing;
    }

    public void setUserFollowing(boolean userFollowing) {
        this.userFollowing = userFollowing;
    }
    public CheckUserFollowingResponse() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CheckUserFollowingResponse that = (CheckUserFollowingResponse) o;
        return userFollowing == that.userFollowing;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userFollowing);
    }
}
