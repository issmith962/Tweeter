package edu.byu.cs.tweeter.net;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import edu.byu.cs.tweeter.BuildConfig;
import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.CheckUserFollowingRequest;
import edu.byu.cs.tweeter.net.request.FeedRequest;
import edu.byu.cs.tweeter.net.request.FollowUserRequest;
import edu.byu.cs.tweeter.net.request.FolloweeCountRequest;
import edu.byu.cs.tweeter.net.request.FollowerCountRequest;
import edu.byu.cs.tweeter.net.request.FollowersRequest;
import edu.byu.cs.tweeter.net.request.FollowingRequest;
import edu.byu.cs.tweeter.net.request.GetAllUsersRequest;
import edu.byu.cs.tweeter.net.request.GetUserRequest;
import edu.byu.cs.tweeter.net.request.LoginRequest;
import edu.byu.cs.tweeter.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.net.request.RegisterRequest;
import edu.byu.cs.tweeter.net.request.StartUpRequest;
import edu.byu.cs.tweeter.net.request.StoryRequest;
import edu.byu.cs.tweeter.net.request.UnfollowUserRequest;
import edu.byu.cs.tweeter.net.response.CheckUserFollowingResponse;
import edu.byu.cs.tweeter.net.response.FeedResponse;
import edu.byu.cs.tweeter.net.response.FollowUserResponse;
import edu.byu.cs.tweeter.net.response.FolloweeCountResponse;
import edu.byu.cs.tweeter.net.response.FollowerCountResponse;
import edu.byu.cs.tweeter.net.response.FollowersResponse;
import edu.byu.cs.tweeter.net.response.FollowingResponse;
import edu.byu.cs.tweeter.net.response.GetAllUsersResponse;
import edu.byu.cs.tweeter.net.response.GetUserResponse;
import edu.byu.cs.tweeter.net.response.LoginResponse;
import edu.byu.cs.tweeter.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.net.response.RegisterResponse;
import edu.byu.cs.tweeter.net.response.StartUpResponse;
import edu.byu.cs.tweeter.net.response.StoryResponse;
import edu.byu.cs.tweeter.net.response.UnfollowUserResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {
    private static List<User> allUsers;
    private static List<String> allAliases;
    private static Map<User, List<User>> followeesByFollower;
    private static Map<User, List<User>> followersByFollowee;
    private static Map<User, List<Status>> statusesByUser;
    private static Map<User, String> passwordsByUser;

    private static boolean story_initialized = false;
    private static boolean follows_initialized = false;
    private static boolean feed_initialized = false;
    private static boolean login_initialized = false;

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
    public FollowingResponse getFollowees(FollowingRequest request) throws AssertionError {

        // Used in place of assert statements because Android does not support them
        if (BuildConfig.DEBUG) {
            if (request.getLimit() < 0) {
                throw new AssertionError();
            }

            if (request.getFollower() == null) {
                throw new AssertionError();
            }
        }

        if (followeesByFollower == null) {
            Map[] maps = initializeFollows();
            followeesByFollower = maps[0];
            followersByFollowee = maps[1];
        }

        List<User> followees = followeesByFollower.get(request.getFollower());

        List<User> allFollowees = followeesByFollower.get(request.getFollower());
        List<User> responseFollowees = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            if (allFollowees != null) {
                int followeesIndex = getFolloweesStartingIndex(request.getLastFollowee(), allFollowees);

                for (int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
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

        if (lastFollowee != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowees.size(); i++) {
                if (lastFollowee.equals(allFollowees.get(i))) {
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
     *
     * @return two maps, one with followers as keys, one with followees as keys
     */
    private Map[] initializeFollows() {
        follows_initialized = true;
        if (allUsers == null) {
            allUsers = new ArrayList<>();
        }
        if (passwordsByUser == null) {
            passwordsByUser = new HashMap<>();
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
        allAliases = new ArrayList<>();
        for (User user : allUsers) {
            allAliases.add(user.getAlias());
        }
        // Populate a map of followees, keyed by follower so we can easily handle followee requests
        for (Follow follow : follows1) {
            List<User> followees = followeesByFollower.get(follow.getFollower());

            if (followees == null) {
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
    /*
    private Map<User, List<User>> initializeFollowers(Map<User, List<User>> followeesByFollower) {
        Map<User, List<User>> followersByFollowee = new HashMap<>();
        for (Map.Entry<User, List<User>> entry : followeesByFollower.entrySet()) {
            User follower = entry.getKey();
            List<User> followees = entry.getValue();
            for (User followee : followees) {
                if (followersByFollowee.containsKey(followee)) {
                    followersByFollowee.get(followee).add(follower);
                } else {
                    List<User> followers = new ArrayList<>();
                    followers.add(follower);
                    followersByFollowee.put(followee, followers);
                }
            }
        }
        return followersByFollowee;
    }
    */

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
        if (BuildConfig.DEBUG) {
            if (request.getLimit() < 0) {
                throw new AssertionError();
            }

            if (request.getFollowee() == null) {
                throw new AssertionError();
            }
        }

        if (followersByFollowee == null) {
            Map[] maps = initializeFollows();
            followeesByFollower = maps[0];
            followersByFollowee = maps[1];
        }

        List<User> allFollowers = followersByFollowee.get(request.getFollowee());
        List<User> responseFollowers = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            if (allFollowers != null) {
                int followersIndex = getFollowersStartingIndex(request.getLastFollower(), allFollowers);

                for (int limitCounter = 0; followersIndex < allFollowers.size() && limitCounter < request.getLimit(); followersIndex++, limitCounter++) {
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

        if (lastFollower != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowers.size(); i++) {
                if (lastFollower.equals(allFollowers.get(i))) {
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
     * <p>
     * Once the backend is put in, this will get each of these from the database. NONE OF THEM
     * CAN BE EMPTY STRINGS
     *
     * @return ArrayList with correct alias, password, authtoken, first name, last name, and
     * imageURL. (in that order)
     */
    private void initializeLogin() {
        login_initialized = true;
        if (passwordsByUser == null) {
            passwordsByUser = new HashMap<>();
        }
        passwordsByUser.put(new User("Test", "User", "@TestUser",
                        "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png"),
                "test_password");
    }

    /**
     * Checks the user input for alias and password and checks them against
     * the proper alias and password.
     *
     * @param loginRequest containing the user input alias and password.
     * @return LoginResponse. If successful, response contains all of user's key info. If not, re
     */
    public LoginResponse checkLogin(LoginRequest loginRequest) {
        if (!login_initialized) {
            initializeLogin();
        }
        if (allAliases.contains(loginRequest.getAlias())) {
            User user = allUsers.get(findIndexOfUser(loginRequest.getAlias()));
            if (passwordsByUser.containsKey(user)) {
                if (passwordsByUser.get(user).equals(loginRequest.getPassword())) {
                    if (user.getImageUri() == null) {
                        return new LoginResponse("Login Successful!", loginRequest.getAlias(),
                                loginRequest.getPassword(), "test_auth", user.getFirstName(),
                                user.getLastName(), user.getImageUrl());
                    } else {
                        return new LoginResponse("Login Successful!", loginRequest.getAlias(),
                                loginRequest.getPassword(), "test_auth", user.getFirstName(),
                                user.getLastName(), user.getImageUri());
                    }
                }
            }
        }
        return new LoginResponse("Login Failure!\nIncorrect alias or password");
    }

    public int findIndexOfUser(String alias) {
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getAlias().equals(alias)) {
                return i;
            }
        }
        return -1;
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
            } else if (i < 10) {
                testStatuses.add(new Status(user, "0" + Integer.toString(i) + "/23/1996", "status number: " + Integer.toString(i)));
            } else {
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
        if (BuildConfig.DEBUG) {
            if (request.getLimit() < 0) {
                throw new AssertionError();
            }

            if (request.getUser() == null) {
                throw new AssertionError();
            }
        }

        if (!story_initialized) {
            initializeStory();
        }
        List<Status> allStatuses = statusesByUser.get(request.getUser());
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            if (allStatuses != null) {
                Collections.sort(allStatuses);
                Collections.reverse(allStatuses);
                int statusIndex = getStatusStartingIndex(request.getLastStatus(), allStatuses);
                for (int limitCounter = 0; statusIndex < allStatuses.size() && limitCounter < request.getLimit(); statusIndex++, limitCounter++) {
                    responseStatuses.add(allStatuses.get(statusIndex));
                }
                hasMorePages = statusIndex < allStatuses.size();
            }
        }
        return new StoryResponse(responseStatuses, hasMorePages);
    }

    private int getStatusStartingIndex(Status lastStatus, List<Status> allStatuses) {
        int statusIndex = 0;

        if (lastStatus != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if (lastStatus.equals(allStatuses.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    statusIndex = i + 1;
                }
            }
        }

        return statusIndex;
    }

    //---------------------------------------------------------------------------------------

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void initializeFeed() {
        feed_initialized = true;
        User user = new User("Test", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        //give test user's followees one status each
        if (statusesByUser == null) {
            statusesByUser = new HashMap<>();
        }
        if (followeesByFollower == null) {
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

                // Random Date Generator
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                long minDay = LocalDate.of(1990, 1, 1).toEpochDay();
                long maxDay = LocalDate.of(2020, 10, 1).toEpochDay();
                long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
                LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
                String date = dtf.format(randomDate).substring(0, 10);


                statusesOfFollowee.add(new Status(followee, date, "status number: " + Integer.toString(1)));
                statusesByUser.put(followee, statusesOfFollowee);
                if (BuildConfig.DEBUG) {
                    if (statusesByUser.get(followee) == null) {
                        throw new AssertionError();
                    }
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public FeedResponse getFeed(FeedRequest request) {
        // Used in place of assert statements because Android does not support them
        if (BuildConfig.DEBUG) {
            if (request.getLimit() < 0) {
                throw new AssertionError();
            }

            if (request.getUser() == null) {
                throw new AssertionError();
            }
        }
        if (!feed_initialized) {
            initializeFeed();
        }
        List<Status> allFolloweeStatuses = new ArrayList<>();
        for (Map.Entry<User, List<Status>> entry : statusesByUser.entrySet()) {
            if (entry.getValue() == null) {
                entry.setValue(new ArrayList<Status>());
            } else {
                if (followeesByFollower.get(request.getUser()) != null) {
                    if (followeesByFollower.get(request.getUser()).contains(entry.getKey())) {
                        allFolloweeStatuses.addAll(entry.getValue());
                    }
                }
            }
        }
        Collections.sort(allFolloweeStatuses);
        Collections.reverse(allFolloweeStatuses);
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            int statusIndex = getStatusStartingIndex(request.getLastStatus(), allFolloweeStatuses);
            for (int limitCounter = 0; statusIndex < allFolloweeStatuses.size() && limitCounter < request.getLimit(); statusIndex++, limitCounter++) {
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
        } else {
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
            Collections.sort(statusesByUser.get(request.getUser()));
            Collections.reverse(statusesByUser.get(request.getUser()));

            return new PostStatusResponse("Status successfully posted!");
        } else {
            return new PostStatusResponse("Status posting failed...");
        }
    }

    //-------------------------------------------------------------------------------------------

    public UnfollowUserResponse unfollowUser(UnfollowUserRequest request) {

        try {
            if (followersByFollowee.get(request.getFollowee()) == null) {
                followersByFollowee.put(request.getFollowee(), new ArrayList<>());
            }
            if (followeesByFollower.get(request.getFollower()) == null) {
                followeesByFollower.put(request.getFollower(), new ArrayList<>());
            }
            if (!followersByFollowee.get(request.getFollowee()).contains(request.getFollower())) {
                return new UnfollowUserResponse("Already not following!");
            }
            followeesByFollower.get(request.getFollower()).remove(request.getFollowee());
            followersByFollowee.get(request.getFollowee()).remove(request.getFollower());
        } catch (Exception e) {
            return new UnfollowUserResponse("Unfollow Failed!");
        }
        return new UnfollowUserResponse(request.getFollowee().getName() + " unfollowed!");
    }

    public FollowUserResponse followUser(FollowUserRequest request) {
        try {
            if (followersByFollowee.get(request.getFollowee()) == null) {
                followersByFollowee.put(request.getFollowee(), new ArrayList<>());
            }
            if (followeesByFollower.get(request.getFollower()) == null) {
                followeesByFollower.put(request.getFollower(), new ArrayList<>());
            }
            if (followersByFollowee.get(request.getFollowee()).contains(request.getFollower())) {
                return new FollowUserResponse("Already Following!");
            }
            followeesByFollower.get(request.getFollower()).add(request.getFollowee());
            followersByFollowee.get(request.getFollowee()).add(request.getFollower());
        } catch (Exception e) {
            return new FollowUserResponse("Follow Failed!");
        }
        return new FollowUserResponse(request.getFollowee().getName() + " followed!");
    }

    public GetAllUsersResponse getAllUsers(GetAllUsersRequest request) {
        return new GetAllUsersResponse(allUsers);
    }

    public FollowerCountResponse getFollowerCount(FollowerCountRequest request) {
        if (followersByFollowee == null) {
            return new FollowerCountResponse(0);
        }
        else {
            List<User> followers = followersByFollowee.get(request.getUser());
            if (followers == null) {
                return new FollowerCountResponse(0);
            }
            else {
                return new FollowerCountResponse(followers.size());

            }
        }

    }

    public FolloweeCountResponse getFolloweeCount(FolloweeCountRequest request) {
        if (followeesByFollower == null) {
            return new FolloweeCountResponse(0);
        }
        else {
            List<User> followees = followeesByFollower.get(request.getUser());
            if (followees == null) {
                return new FolloweeCountResponse(0);
            }
            else {
                return new FolloweeCountResponse(followees.size());

            }
        }

    }
                                                  //------------------------------------------------------------------------------------------

    public RegisterResponse registerUser(RegisterRequest request) {
        // upload uri to server get get URL back?
        User newUser;
        try {
            newUser = new User(request.getName().split(" ")[0], request.getName().split(" ")[1],
                    request.getAlias(), request.getImageUri());
        } catch (Exception e) {
            newUser = null;
        }
        if (newUser != null) {
            allUsers.add(newUser);
            allAliases.add(newUser.getAlias());
            passwordsByUser.put(newUser, request.getPassword());
            followeesByFollower.put(newUser, new ArrayList<User>());
            followersByFollowee.put(newUser, new ArrayList<User>());
            statusesByUser.put(newUser, new ArrayList<Status>());
        }
        return new RegisterResponse(newUser, request.getPassword());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public StartUpResponse startUp(StartUpRequest request) {
        try {
            if (!follows_initialized) {
                Map[] maps = initializeFollows();
                followeesByFollower = maps[0];
                followersByFollowee = maps[1];
            }
            if (statusesByUser == null) {
                statusesByUser = new HashMap<>();
                initializeStory();
                initializeFeed();
            }
            if (allUsers == null) {
                allUsers = new ArrayList<>();
            }
            if (allAliases == null) {
                allAliases = new ArrayList<>();
            }
            if (passwordsByUser == null) {
                passwordsByUser = new HashMap<>();
            }
            return new StartUpResponse("Success!");
        } catch (Exception e) {
            e.printStackTrace();
            return new StartUpResponse("Failure!");
        }

    }

    public GetUserResponse getUser(GetUserRequest request) {
        for (User user : allUsers) {
            if (request.getAlias().equals(user.getAlias())) {
                return new GetUserResponse(user);
            }
        }
        return new GetUserResponse(null);
    }

    public void testInitialize(List<Follow> follows, Map<User, List<Status>> statusesByUser, Map<User, String> passwordsByUser) {
        ServerFacade.statusesByUser = statusesByUser;
        ServerFacade.passwordsByUser = passwordsByUser;

        if (allUsers == null) {
            allUsers = new ArrayList<>();
        }
        for (Follow follow : follows) {
            if (!allUsers.contains(follow.getFollowee())) {
                allUsers.add(follow.getFollowee());
            }
            if (!allUsers.contains(follow.getFollower())) {
                allUsers.add(follow.getFollower());
            }
        }
        allAliases = new ArrayList<>();
        for (User user : allUsers) {
            allAliases.add(user.getAlias());
        }

        Map<User, List<User>> fByer = new HashMap<>();
        Map<User, List<User>> fByee = new HashMap<>();
        for (Follow follow : follows) {
            List<User> followees = fByer.get(follow.getFollower());

            if (followees == null) {
                followees = new ArrayList<>();
                fByer.put(follow.getFollower(), followees);
            }

            followees.add(follow.getFollowee());
        }
        for (Follow follow : follows) {
            List<User> followers = fByee.get(follow.getFollowee());

            if (followers == null) {
                followers = new ArrayList<>();
                fByee.put(follow.getFollowee(), followers);
            }
            followers.add(follow.getFollower());
        }
        followeesByFollower = fByer;
        followersByFollowee = fByee;
    }

    public void testInitialize(List<Follow> follows, Map<User, List<Status>> statusesByUser, Map<User, String> passwordsByUser, List<User> allUsers) {
        ServerFacade.statusesByUser = statusesByUser;
        ServerFacade.passwordsByUser = passwordsByUser;

        ServerFacade.allUsers = new ArrayList<>();
        ServerFacade.allUsers = allUsers;
        allAliases = new ArrayList<>();
        for (User user : allUsers) {
            allAliases.add(user.getAlias());
        }

        Map<User, List<User>> fByer = new HashMap<>();
        Map<User, List<User>> fByee = new HashMap<>();
        for (Follow follow : follows) {
            List<User> followees = fByer.get(follow.getFollower());

            if (followees == null) {
                followees = new ArrayList<>();
                fByer.put(follow.getFollower(), followees);
            }

            followees.add(follow.getFollowee());
        }
        for (Follow follow : follows) {
            List<User> followers = fByee.get(follow.getFollowee());

            if (followers == null) {
                followers = new ArrayList<>();
                fByee.put(follow.getFollowee(), followers);
            }
            followers.add(follow.getFollower());
        }
        followeesByFollower = fByer;
        followersByFollowee = fByee;
    }
}