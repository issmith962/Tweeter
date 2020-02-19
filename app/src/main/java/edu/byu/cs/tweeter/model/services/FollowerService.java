package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.FollowersRequest;
import edu.byu.cs.tweeter.net.request.FollowingRequest;
import edu.byu.cs.tweeter.net.response.FollowersResponse;
import edu.byu.cs.tweeter.net.response.FollowingResponse;

/**
 * Contains the business logic for getting the users a user has as followers.
 */
public class FollowerService {

    /**
     * The singleton instance.
     */
    private static FollowerService instance;

    private final ServerFacade serverFacade;

    /**
     * Return the singleton instance of this class.
     *
     * @return the instance.
     */
    public static FollowerService getInstance() {
        if(instance == null) {
            instance = new FollowerService();
        }

        return instance;
    }

    /**
     * A private constructor created to ensure that this class is a singleton (i.e. that it
     * cannot be instantiated by external classes).
     */
    private FollowerService() {
        serverFacade = new ServerFacade();
    }

    /**
     * Returns the users that the user specified in the request has as followers. Uses information in
     * the request object to limit the number of followers returned and to return the next set of
     * followers after any that were returned in a previous request. Uses the {@link ServerFacade} to
     * get the followers from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public FollowersResponse getFollowers(FollowersRequest request) {
        return serverFacade.getFollowers(request);
    }
}
