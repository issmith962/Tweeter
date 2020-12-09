package byu.edu.cs.tweeter.shared.request;

import byu.edu.cs.tweeter.shared.model.domain.User;

/**
 * Contains all the information needed to make a request to have the server return the next page of
 * followers for a specified followee.
 */
public class FollowersRequest {
    private User followee;
    private int limit;
    private User lastFollower;

    /**
     * Creates an instance.
     *
     * @param followee the {@link User} whose followers are to be returned.
     * @param limit the maximum number of followers to return.
     * @param lastFollower the last followee that was returned in the previous request (null if
     *                     there was no previous request or if no followers were returned in the
     *                     previous request).
     */
    public FollowersRequest(User followee, int limit, User lastFollower) {
        this.followee = followee;
        this.limit = limit;
        this.lastFollower = lastFollower;
    }

    /**
     * Returns the followee whose followers are to be returned by this request.
     *
     * @return the followee.
     */
    public User getFollowee() {
        return followee;
    }

    /**
     * Returns the number representing the maximum number of followers to be returned by this request.
     *
     * @return the limit.
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Returns the last followee that was returned in the previous request or null if there was no
     * previous request or if no followers were returned in the previous request.
     *
     * @return the last followee.
     */
    public User getLastFollower() {
        return lastFollower;
    }

    public FollowersRequest() {
        this.followee = null;
        this.limit = 0;
        this.lastFollower = null;

    }

    public void setFollowee(User followee) {
        this.followee = followee;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setLastFollower(User lastFollower) {
        this.lastFollower = lastFollower;
    }
}

