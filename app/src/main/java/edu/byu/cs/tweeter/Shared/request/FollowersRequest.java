package edu.byu.cs.tweeter.Shared.request;

import edu.byu.cs.tweeter.Shared.domain.User;

/**
 * Contains all the information needed to make a request to have the server return the next page of
 * followers for a specified followee.
 */
public class FollowersRequest {
    private final User followee;
    private final int limit;
    private final User lastFollower;

    /**
     * Creates an instance.
     *
     * @param follower the {@link User} whose followers are to be returned.
     * @param limit the maximum number of followers to return.
     * @param lastFollower the last followee that was returned in the previous request (null if
     *                     there was no previous request or if no followers were returned in the
     *                     previous request).
     */
    public FollowersRequest(User follower, int limit, User lastFollower) {
        this.followee = follower;
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
}

