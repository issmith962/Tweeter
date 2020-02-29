package edu.byu.cs.tweeter.net;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.byu.cs.tweeter.BuildConfig;
import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.CheckUserFollowingRequest;
import edu.byu.cs.tweeter.net.request.FeedRequest;
import edu.byu.cs.tweeter.net.request.FollowersRequest;
import edu.byu.cs.tweeter.net.request.FollowingRequest;
import edu.byu.cs.tweeter.net.request.LoginRequest;
import edu.byu.cs.tweeter.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.net.request.StoryRequest;
import edu.byu.cs.tweeter.net.response.CheckUserFollowingResponse;
import edu.byu.cs.tweeter.net.response.FeedResponse;
import edu.byu.cs.tweeter.net.response.FollowersResponse;
import edu.byu.cs.tweeter.net.response.FollowingResponse;
import edu.byu.cs.tweeter.net.response.LoginResponse;
import edu.byu.cs.tweeter.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.net.response.StoryResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {
    private static List<User> allUsers;
    private static Map<User, List<User>> followeesByFollower;
    private static Map<User, List<User>> followersByFollowee;
    private static List<String> correctLoginInfo;
    private static Map<User, List<Status>> statusesByUser;
    private static boolean story_initialized = false;
    private static boolean feed_initialized = false;

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the followees.
     */
    public FollowingResponse getFollowees(FollowingRequest request) {

        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getFollower() == null) {
                throw new AssertionError();
            }
        }

        if(followeesByFollower == null) {
            Map[] maps = initializeFollows();
            followeesByFollower = maps[0];
            followersByFollowee = maps[1];
        }

        List<User> allFollowees = followeesByFollower.get(request.getFollower());
        List<User> responseFollowees = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allFollowees != null) {
                int followeesIndex = getFolloweesStartingIndex(request.getLastFollowee(), allFollowees);

                for(int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                    responseFollowees.add(allFollowees.get(followeesIndex));
                }

                hasMorePages = followeesIndex < allFollowees.size();
            }
        }

        return new FollowingResponse(responseFollowees, hasMorePages);
    }

    /**
     * Determines the index for the first followee in the specified 'allFollowees' list that should
     * be returned in the current request. This will be the index of the next followee after the
     * specified 'lastFollowee'.
     *
     * @param lastFollowee the last followee that was returned in the previous request or null if
     *                     there was no previous request.
     * @param allFollowees the generated list of followees from which we are returning paged results.
     * @return the index of the first followee to be returned.
     */
    private int getFolloweesStartingIndex(User lastFollowee, List<User> allFollowees) {

        int followeesIndex = 0;

        if(lastFollowee != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowees.size(); i++) {
                if(lastFollowee.equals(allFollowees.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followeesIndex = i + 1;
                }
            }
        }

        return followeesIndex;
    }

    /**
     * Generates the follow data.
     * @return two maps, one with followers as keys, one with followees as keys
     */
    private Map[] initializeFollows() {
        if (allUsers == null) {
            allUsers = new ArrayList<>();
        }
        Map<User, List<User>> followeesByFollower = new HashMap<>();
        Map<User, List<User>> followersByFollowee = new HashMap<>();

        List<Follow> follows1 = getFollowGenerator().sortFollows(getFollowGenerator().generateUsersAndFollows(100,
                0, 50), FollowGenerator.Sort.FOLLOWER_FOLLOWEE);
        List<Follow> follows2 = getFollowGenerator().sortFollows(follows1, FollowGenerator.Sort.FOLLOWER_FOLLOWEE);
        for (Follow follow : follows1) {
            if (!allUsers.contains(follow.getFollowee())) {
                allUsers.add(follow.getFollowee());
            }
            if (!allUsers.contains(follow.getFollower())) {
                allUsers.add(follow.getFollower());
            }
        }
        for (Follow follow : follows2) {
            if (!allUsers.contains(follow.getFollowee())) {
                allUsers.add(follow.getFollowee());
            }
            if (!allUsers.contains(follow.getFollower())) {
                allUsers.add(follow.getFollower());
            }
        }
        // Populate a map of followees, keyed by follower so we can easily handle followee requests
        for(Follow follow : follows1) {
            List<User> followees = followeesByFollower.get(follow.getFollower());

            if(followees == null) {
                followees = new ArrayList<>();
                followeesByFollower.put(follow.getFollower(), followees);
            }

            followees.add(follow.getFollowee());
        }

        for (Follow follow : follows2) {
            List<User> followers = followersByFollowee.get(follow.getFollowee());

            if (followers == null) {
                followers = new ArrayList<>();
                followersByFollowee.put(follow.getFollowee(), followers);
            }
            followers.add(follow.getFollower());
        }
        return new Map[]{followeesByFollower, followersByFollowee};
    }

    /**
     * Takes the initializeFollows generated data and reverses the map to be keyed by followee, rather
     * than by follower.
     */
//    private Map<User, List<User>> initializeFollowers(Map<User, List<User>> followeesByFollower) {
//        Map<User, List<User>> followersByFollowee = new HashMap<>();
//        for (Map.Entry<User, List<User>> entry : followeesByFollower.entrySet()) {
//            User follower = entry.getKey();
//            List<User> followees = entry.getValue();
//            for (User followee : followees) {
//                if (followersByFollowee.containsKey(followee)) {
//                    followersByFollowee.get(followee).add(follower);
//                } else {
//                    List<User> followers = new ArrayList<>();
//                    followers.add(follower);
//                    followersByFollowee.put(followee, followers);
//                }
//            }
//        }
//        return followersByFollowee;
//    }
    /**
     * Returns an instance of FollowGenerator that can be used to generate Follow data. This is
     * written as a separate method to allow mocking of the generator.
     *
     * @return the generator.
     */
    FollowGenerator getFollowGenerator() {
        return FollowGenerator.getInstance();
    }


    //----------------------------------------------------------------------------------------------

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followers returned and to return the next set of
     * followers after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose followers are to be returned and any
     *                other information required to satisfy the request.
     * @return the followers.
     */
    public FollowersResponse getFollowers(FollowersRequest request) {
        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getFollower() == null) {
                throw new AssertionError();
            }
        }

        if(followersByFollowee == null) {
            Map[] maps = initializeFollows();
            followeesByFollower = maps[0];
            followersByFollowee = maps[1];
        }

        List<User> allFollowers = followersByFollowee.get(request.getFollower());
        List<User> responseFollowers = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allFollowers != null) {
                int followersIndex = getFollowersStartingIndex(request.getLastFollower(), allFollowers);

                for(int limitCounter = 0; followersIndex < allFollowers.size() && limitCounter < request.getLimit(); followersIndex++, limitCounter++) {
                    responseFollowers.add(allFollowers.get(followersIndex));
                }

                hasMorePages = followersIndex < allFollowers.size();
            }
        }

        return new FollowersResponse(responseFollowers, hasMorePages);
    }

    /**
     * Determines the index for the first follower in the specified 'allFollowers' list that should
     * be returned in the current request. This will be the index of the next follower after the
     * specified 'lastFollower'.
     *
     * @param lastFollower the last follower that was returned in the previous request or null if
     *                     there was no previous request.
     * @param allFollowers the generated list of followers from which we are returning paged results.
     * @return the index of the first follower to be returned
     **/
    private int getFollowersStartingIndex(User lastFollower, List<User> allFollowers) {

        int followersIndex = 0;

        if(lastFollower != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowers.size(); i++) {
                if(lastFollower.equals(allFollowers.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followersIndex = i + 1;
                }
            }
        }

        return followersIndex;
    }

    //--------------------------------------------------------------------------------------

    /**
     * Sets the correct alias, password, authtoken, first name, last name, and profile image URL
     * for Test_User. Initializes the login for test purposes until the facade is no longer needed.
     *
     * Once the backend is put in, this will get each of these from the database. NONE OF THEM
     * CAN BE EMPTY STRINGS
     * @return ArrayList with correct alias, password, authtoken, first name, last name, and
     * imageURL. (in that order)
     */
    private ArrayList<String> initializeLogin() {
        ArrayList<String> correctLoginInfo = new ArrayList<String>();
        correctLoginInfo.add("@TestUser");
        correctLoginInfo.add("test_password");
        correctLoginInfo.add("test_auth");
        correctLoginInfo.add("Test");
        correctLoginInfo.add("User");
        correctLoginInfo.add("https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        return correctLoginInfo;
    }

    /**
     * Checks the user input for alias and password and checks them against
     * the proper alias and password.
     * @param loginRequest containing the user input alias and password.
     * @return LoginResponse. If successful, response contains all of user's key info. If not, re
     */
    public LoginResponse checkLogin(LoginRequest loginRequest) {
        if (correctLoginInfo == null) {
            correctLoginInfo = initializeLogin();
        }
        String alias = correctLoginInfo.get(0);
        String password = correctLoginInfo.get(1);
        String authToken = correctLoginInfo.get(2);
        String firstName = correctLoginInfo.get(3);
        String lastName = correctLoginInfo.get(4);
        String imageURL = correctLoginInfo.get(5);

        if (loginRequest.getAlias().equals(alias) && loginRequest.getPassword().equals(password)) {
            return new LoginResponse("Login Successful!", alias, password, authToken, firstName, lastName, imageURL);
        }
        else {
            return new LoginResponse("Login Failure!\nIncorrect alias or password");
        }
    }

    //-------------------------------------------------------------------------------------------

    public void initializeStory() {
        story_initialized = true;
        User user = new User("Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        //give test user some statuses:
        List<Status> testStatuses = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            if (i == 0) {
                testStatuses.add(new Status(user, "01/01/1991", "status number: " + Integer.toString(i)));
            }
            else if (i < 10) {
                testStatuses.add(new Status(user, "0" + Integer.toString(i) + "/23/1996" , "status number: " + Integer.toString(i)));
            }
            else {
                testStatuses.add(new Status(user, "01/" + Integer.toString(i) + "/1996", "status number: " + Integer.toString(i)));
            }
        }
        if (statusesByUser == null) {
            statusesByUser = new HashMap<>();
        }
        statusesByUser.put(user, testStatuses);
    }


    public StoryResponse getStory(StoryRequest request) {
        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getUser() == null) {
                throw new AssertionError();
            }
        }

        if (!story_initialized) {
            initializeStory();
        }
        List<Status> allStatuses = statusesByUser.get(request.getUser());
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allStatuses != null) {
                int statusIndex = getStatusStartingIndex(request.getLastStatus(), allStatuses);
                for(int limitCounter = 0; statusIndex < allStatuses.size() && limitCounter < request.getLimit(); statusIndex++, limitCounter++) {
                    responseStatuses.add(allStatuses.get(statusIndex));
                }
                hasMorePages = statusIndex < allStatuses.size();
            }
        }

        return new StoryResponse(responseStatuses, hasMorePages);
    }

    private int getStatusStartingIndex(Status lastStatus, List<Status> allStatuses) {
        int statusIndex = 0;

        if(lastStatus != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if(lastStatus.equals(allStatuses.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    statusIndex = i + 1;
                }
            }
        }

        return statusIndex;
    }

    //---------------------------------------------------------------------------------------

    public void initializeFeed() {
        feed_initialized = true;
        User user = new User("Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        //give test user's followees one status each
        if (statusesByUser == null) {
            statusesByUser = new HashMap<>();
        }
        if(followeesByFollower == null) {
            Map[] maps = initializeFollows();
            followeesByFollower = maps[0];
            followersByFollowee = maps[1];
        }
        List<User> followeesOfTestUser = followeesByFollower.get(user);
        if (followeesOfTestUser != null) {
            for (User followee : followeesOfTestUser) {
                List<Status> statusesOfFollowee = statusesByUser.get(followee);
                if (statusesOfFollowee == null) {
                    statusesOfFollowee = new ArrayList<>();
                }
                statusesOfFollowee.add(new Status(followee, "date" + Integer.toString(1), "status number: " + Integer.toString(1)));
                statusesByUser.put(followee, statusesOfFollowee);
                if(BuildConfig.DEBUG) {
                    if (statusesByUser.get(followee) == null) {
                        throw new AssertionError();
                    }
                }
            }
        }
    }


    public FeedResponse getFeed(FeedRequest request) {
        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getUser() == null) {
                throw new AssertionError();
            }
        }

        initializeFeed();

        List<Status> allFolloweeStatuses = new ArrayList<>();
        for (Map.Entry<User, List<Status>> entry : statusesByUser.entrySet()) {
            if (Objects.requireNonNull(followeesByFollower.get(request.getUser())).contains(entry.getKey())) {
                allFolloweeStatuses.addAll(entry.getValue());
            }
        }
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int statusIndex = getStatusStartingIndex(request.getLastStatus(), allFolloweeStatuses);
            for(int limitCounter = 0; statusIndex < allFolloweeStatuses.size() && limitCounter < request.getLimit(); statusIndex++, limitCounter++) {
                responseStatuses.add(allFolloweeStatuses.get(statusIndex));
            }
            hasMorePages = statusIndex < allFolloweeStatuses.size();
        }
        boolean t = true;
        return new FeedResponse(responseStatuses, hasMorePages);
    }
    //------------------------------------------------------------------------------------------

    public CheckUserFollowingResponse isUserFollowing(CheckUserFollowingRequest request) {
        User follower = request.getFollower();
        String followeeAlias = request.getFolloweeAlias();
        if (followeesByFollower == null) {
            return new CheckUserFollowingResponse(false);
        }
        if (followeesByFollower.get(follower) == null) {
            return new CheckUserFollowingResponse(false);
        }
        else {
            for (User followee : Objects.requireNonNull(followeesByFollower.get(follower))) {
                if (followee.getAlias().equals(request.getFolloweeAlias())) {
                    return new CheckUserFollowingResponse(true);
                }
            }
        }
        return new CheckUserFollowingResponse(false);
    }

    // -----------------------------------------------------------------------------------------

    @RequiresApi(api = Build.VERSION_CODES.O)
    public PostStatusResponse postStatus(PostStatusRequest request) {
        if (!request.getNewStatus().equals("") && allUsers.contains(request.getUser())) {
            User user = request.getUser();
            if (!statusesByUser.containsKey(user)) {
                statusesByUser.put(user, new ArrayList<Status>());
            }
            Status newStatus = new Status(request.getUser(), request.getDate(), request.getNewStatus());
            statusesByUser.get(request.getUser()).add(newStatus);
            return new PostStatusResponse("Status successfully posted!");
        }
        else {
            return new PostStatusResponse("Status posting failed...");
        }
    }



}