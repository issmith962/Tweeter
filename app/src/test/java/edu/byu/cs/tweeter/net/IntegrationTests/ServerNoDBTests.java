package edu.byu.cs.tweeter.net.IntegrationTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.Status;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.CheckUserFollowingRequest;
import byu.edu.cs.tweeter.shared.request.FeedRequest;
import byu.edu.cs.tweeter.shared.request.FollowUserRequest;
import byu.edu.cs.tweeter.shared.request.FolloweeCountRequest;
import byu.edu.cs.tweeter.shared.request.FollowerCountRequest;
import byu.edu.cs.tweeter.shared.request.FollowersRequest;
import byu.edu.cs.tweeter.shared.request.FollowingRequest;
import byu.edu.cs.tweeter.shared.request.GetUserRequest;
import byu.edu.cs.tweeter.shared.request.LoginRequest;
import byu.edu.cs.tweeter.shared.request.LogoutRequest;
import byu.edu.cs.tweeter.shared.request.PostStatusRequest;
import byu.edu.cs.tweeter.shared.request.RegisterRequest;
import byu.edu.cs.tweeter.shared.request.StoryRequest;
import byu.edu.cs.tweeter.shared.request.UnfollowUserRequest;
import byu.edu.cs.tweeter.shared.response.CheckUserFollowingResponse;
import byu.edu.cs.tweeter.shared.response.FeedResponse;
import byu.edu.cs.tweeter.shared.response.FollowUserResponse;
import byu.edu.cs.tweeter.shared.response.FolloweeCountResponse;
import byu.edu.cs.tweeter.shared.response.FollowerCountResponse;
import byu.edu.cs.tweeter.shared.response.FollowersResponse;
import byu.edu.cs.tweeter.shared.response.FollowingResponse;
import byu.edu.cs.tweeter.shared.response.GetUserResponse;
import byu.edu.cs.tweeter.shared.response.LoginResponse;
import byu.edu.cs.tweeter.shared.response.LogoutResponse;
import byu.edu.cs.tweeter.shared.response.PostStatusResponse;
import byu.edu.cs.tweeter.shared.response.RegisterResponse;
import byu.edu.cs.tweeter.shared.response.StoryResponse;
import byu.edu.cs.tweeter.shared.response.UnfollowUserResponse;
import edu.byu.cs.tweeter.Client.model.services.CheckUserFollowingServiceProxy;
import edu.byu.cs.tweeter.Client.model.services.FeedServiceProxy;
import edu.byu.cs.tweeter.Client.model.services.FollowActionServiceProxy;
import edu.byu.cs.tweeter.Client.model.services.FollowerServiceProxy;
import edu.byu.cs.tweeter.Client.model.services.FollowingServiceProxy;
import edu.byu.cs.tweeter.Client.model.services.GetUserServiceProxy;
import edu.byu.cs.tweeter.Client.model.services.LoginServiceProxy;
import edu.byu.cs.tweeter.Client.model.services.LogoutServiceProxy;
import edu.byu.cs.tweeter.Client.model.services.PostStatusServiceProxy;
import edu.byu.cs.tweeter.Client.model.services.RegisterServiceProxy;
import edu.byu.cs.tweeter.Client.model.services.StoryServiceProxy;

public class ServerNoDBTests {
    private String MALE_IMAGE_URL;
    private String FEMALE_IMAGE_URL;
    private User testUser;
    private AuthToken testAuthToken;

    // Followees of Default Dummy User
    private User user1;
    private User user2;
    private User user3;
    private User user4;
    private User user5;
    private User user6;
    private User user7;
    private User user8;
    private User user9;
    private User user10;
    private User user11;
    private User user12;
    private User user13;
    private User user14;
    private User user15;
    private User user16;
    private User user17;
    private User user18;
    private User user19;
    private User user20;

    // Followers of Default Dummy User
    private User user21;
    private User user22;
    private User user23;
    private User user24;
    private User user25;
    private User user26;
    private User user27;
    private User user28;
    private User user29;
    private User user30;
    private User user31;
    private User user32;
    private User user33;
    private User user34;
    private User user35;
    private User user36;
    private User user37;
    private User user38;
    private User user39;
    private User user40;

    @BeforeEach
    public void setup() {
        MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
        FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

        testAuthToken = new AuthToken("test authToken");
        testUser = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        // Followees of Default Dummy User
        user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
        user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
        user3 = new User("Bob", "Bobson", MALE_IMAGE_URL);
        user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
        user5 = new User("Chris", "Colston", MALE_IMAGE_URL);
        user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL);
        user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL);
        user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL);
        user9 = new User("Elliott", "Enderson", MALE_IMAGE_URL);
        user10 = new User("Elizabeth", "Engle", FEMALE_IMAGE_URL);
        user11 = new User("Frank", "Frandson", MALE_IMAGE_URL);
        user12 = new User("Fran", "Franklin", FEMALE_IMAGE_URL);
        user13 = new User("Gary", "Gilbert", MALE_IMAGE_URL);
        user14 = new User("Giovanna", "Giles", FEMALE_IMAGE_URL);
        user15 = new User("Henry", "Henderson", MALE_IMAGE_URL);
        user16 = new User("Helen", "Hopwell", FEMALE_IMAGE_URL);
        user17 = new User("Igor", "Isaacson", MALE_IMAGE_URL);
        user18 = new User("Isabel", "Isaacson", FEMALE_IMAGE_URL);
        user19 = new User("Justin", "Jones", MALE_IMAGE_URL);
        user20 = new User("Jill", "Johnson", FEMALE_IMAGE_URL);

        // Followers of Default Dummy User
        user21 = new User("Yisroel", "Bailey",FEMALE_IMAGE_URL);
        user22 = new User("Kaydan", "Frazier", MALE_IMAGE_URL);
        user23 = new User("Tasnim", "Solomon", MALE_IMAGE_URL);
        user24 = new User("Sachin", "Rich", MALE_IMAGE_URL);
        user25 = new User("Nia", "Davenport",FEMALE_IMAGE_URL);
        user26 = new User("Tobi", "Carlson", MALE_IMAGE_URL);
        user27 = new User("Izzy", "Berg",FEMALE_IMAGE_URL);
        user28 = new User("Rayan", "Mercado", MALE_IMAGE_URL);
        user29 = new User("Glyn", "Lara",FEMALE_IMAGE_URL);
        user30 = new User("Rueben", "Kidd", MALE_IMAGE_URL);
        user31 = new User("Giselle", "Harrison",FEMALE_IMAGE_URL);
        user32 = new User("Felix", "Begum", MALE_IMAGE_URL);
        user33 = new User("Avni", "Beattie",FEMALE_IMAGE_URL);
        user34 = new User("Terrence", "Cash", MALE_IMAGE_URL);
        user35 = new User("Finnian", "Dickinson", MALE_IMAGE_URL);
        user36 = new User("Mamie", "Butler",FEMALE_IMAGE_URL);
        user37 = new User("Kaylan", "Barton", MALE_IMAGE_URL);
        user38 = new User("Roan", "Sutton",FEMALE_IMAGE_URL);
        user39 = new User("Abdurrahman", "Simmons", MALE_IMAGE_URL);
        user40 = new User("Imani", "Burton", MALE_IMAGE_URL);
    }

    private List<User> getDummyFollowees() {
        return Arrays.asList(user1, user2, user3, user4, user5, user6, user7,
                user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18,
                user19, user20);
    }

    private List<User> getDummyFollowers() {
        return Arrays.asList(user21, user22, user23, user24, user25, user26, user27,
                user28, user29, user30, user31, user32, user33, user34, user35, user36, user37, user38,
                user39, user40);
    }

    // Statuses of Default Dummy User (story)
    private List<Status> generateTestUserStatuses() {
        List<Status> testUserStatuses;
        testUserStatuses = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String day;
            if (i + 1< 10) {
                day = "0" + (i + 1);
            }
            else {
                day = Integer.toString(i + 1);
            }
            testUserStatuses.add(new Status(testUser, 1607100054, "Status # " + i));
        }
        return testUserStatuses;
    }

    // Some random statuses of Dummy User Followees (feed)
    private List<Status> generateTestUserFolloweeStatuses() {
        List<Status> followeeStatuses;
        followeeStatuses = new ArrayList<>();
        int i = 0;
        for (User followee : getDummyFollowees()) {
            String day;
            if (i + 1 < 10) {
                day = "0" + (i + 1);
            } else {
                day = Integer.toString(i + 1);
            }
            followeeStatuses.add(new Status(followee, 1607100054, "status #1 @AllenAnderson"));
            i++;
        }
        return followeeStatuses;
    }


    /*
    The following are integration tests to make sure that the dummy responses in my DAO make it
    all the way through the chain back to the client services. I've used the sammy dummy data
    generators here as in the DAO's in order to make sure that the data is identical, and if there
    was an irregularity in a DAO generator, it will be present here as well, just to make sure the
    data will be identical due to server capabilities rather than bugging due to an error in my
    dummy data generator.
     */

    /*
    Each method will be a test for a particular service (NOT service proxy class!!).
        i.e. getFollowers rather than FollowerServiceProxy
     */

    @Test
    public void getFollowees_validRequest() {
        List<User> correctFollowees = getDummyFollowees();
        correctFollowees = correctFollowees.stream().sorted((u1, u2) -> u1.getName().compareToIgnoreCase(u2.getName())).collect(Collectors.toList());
        correctFollowees = correctFollowees.subList(0, 10);

        FollowingResponse successResponse = new FollowingResponse(correctFollowees, true);

        FollowingRequest request = new FollowingRequest(testUser, 10, null);
        FollowingServiceProxy proxy = new FollowingServiceProxy();
        FollowingResponse response;
        try {
            response = proxy.getFollowees(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        }
        Assertions.assertEquals(response, successResponse);
    }

    @Test
    public void getFollowees_invalidRequest() {
        FollowingResponse failureResponse = new FollowingResponse("Bad Request: no user given..");

        FollowingRequest request = new FollowingRequest(null, 10, null);
        FollowingServiceProxy proxy = new FollowingServiceProxy();

        FollowingResponse response;
        try {
            response = proxy.getFollowees(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        } catch (RuntimeException e) {
            response = new FollowingResponse(e.getMessage());
        }
        Assertions.assertEquals(response, failureResponse);
    }

    @Test
    public void getFolloweeCount_validRequest() {
        FolloweeCountResponse successResponse = new FolloweeCountResponse(20);

        FolloweeCountRequest request = new FolloweeCountRequest(testUser);
        FollowingServiceProxy proxy = new FollowingServiceProxy();

        FolloweeCountResponse response;
        try {
            response = proxy.getFolloweeCount(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        }
        Assertions.assertEquals(response, successResponse);
    }

    @Test
    public void getFolloweeCount_invalidRequest() {
        FolloweeCountResponse failureResponse = new FolloweeCountResponse("No user identified..");

        FolloweeCountRequest request = new FolloweeCountRequest(null);
        FollowingServiceProxy proxy = new FollowingServiceProxy();

        FolloweeCountResponse response;
        try {
            response = proxy.getFolloweeCount(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        } catch (RuntimeException e) {
            response = new FolloweeCountResponse(e.getMessage());
        }
        Assertions.assertEquals(response, failureResponse);
    }

    @Test
    public void getFollowers_validRequest() {
        List<User> correctFollowers = getDummyFollowers();
        correctFollowers = correctFollowers.stream().sorted((u1, u2) -> u1.getName().compareToIgnoreCase(u2.getName())).collect(Collectors.toList());
        correctFollowers = correctFollowers.subList(0, 10);
        FollowersResponse successResponse = new FollowersResponse(correctFollowers, true);

        FollowersRequest request = new FollowersRequest(testUser, 10, null);
        FollowerServiceProxy proxy = new FollowerServiceProxy();
        FollowersResponse response;
        try {
            response = proxy.getFollowers(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        }
        Assertions.assertEquals(response, successResponse);
    }

    @Test
    public void getFollowers_invalidRequest() {
        FollowersResponse failureResponse = new FollowersResponse("Bad Request: no user given..");

        FollowersRequest request = new FollowersRequest(null, 10, null);
        FollowerServiceProxy proxy = new FollowerServiceProxy();

        FollowersResponse response;
        try {
            response = proxy.getFollowers(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        } catch (RuntimeException e) {
            response = new FollowersResponse(e.getMessage());
        }
        Assertions.assertEquals(response, failureResponse);
    }

    @Test
    public void getFollowerCount_validRequest() {
        FollowerCountResponse successResponse = new FollowerCountResponse(20);

        FollowerCountRequest request = new FollowerCountRequest(testUser);
        FollowerServiceProxy proxy = new FollowerServiceProxy();

        FollowerCountResponse response;
        try {
            response = proxy.getFollowerCount(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        }
        Assertions.assertEquals(response, successResponse);
    }

    @Test
    public void getFollowerCount_invalidRequest() {
        FollowerCountResponse failureResponse = new FollowerCountResponse("No user identified..");

        FollowerCountRequest request = new FollowerCountRequest(null);
        FollowerServiceProxy proxy = new FollowerServiceProxy();

        FollowerCountResponse response;
        try {
            response = proxy.getFollowerCount(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        } catch (RuntimeException e) {
            response = new FollowerCountResponse(e.getMessage());
        }
        Assertions.assertEquals(response, failureResponse);
    }

    @Test
    public void getFeed_validRequest() {
        List<Status> correctFeed = generateTestUserFolloweeStatuses();
        Collections.sort(correctFeed);
        Collections.reverse(correctFeed);
        correctFeed = correctFeed.subList(0, 10);
        FeedResponse successResponse = new FeedResponse(correctFeed, true);

        FeedRequest request = new FeedRequest(testUser, 10, null, testAuthToken);
        FeedServiceProxy proxy = new FeedServiceProxy();

        FeedResponse response;
        try {
            response = proxy.getFeed(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        }

        Assertions.assertEquals(response, successResponse);
    }

    @Test
    public void getFeed_invalidRequest() {
        FeedResponse failureResponse = new FeedResponse("Bad Request: No statuses requested..");

        FeedRequest request = new FeedRequest(testUser, 0, null, testAuthToken);
        FeedServiceProxy proxy = new FeedServiceProxy();

        FeedResponse response;
        try {
            response = proxy.getFeed(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        } catch (RuntimeException e) {
            response = new FeedResponse(e.getMessage());
        }

        Assertions.assertEquals(response, failureResponse);
    }

    @Test
    public void getStory_validRequest() {
        List<Status> correctStory = generateTestUserStatuses();
        Collections.sort(correctStory);
        Collections.reverse(correctStory);
        correctStory = correctStory.subList(0, 10);
        StoryResponse successResponse = new StoryResponse(correctStory, true);

        StoryRequest request = new StoryRequest(testUser, 10, null);
        StoryServiceProxy proxy = new StoryServiceProxy();

        StoryResponse response;
        try {
            response = proxy.getStory(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        }

        Assertions.assertEquals(response, successResponse);
    }

    @Test
    public void getStory_invalidRequest() {
        StoryResponse failureResponse = new StoryResponse("Bad Request: no statuses requested..");

        StoryRequest request = new StoryRequest(testUser, 0, null);
        StoryServiceProxy proxy = new StoryServiceProxy();

        StoryResponse response;
        try {
            response = proxy.getStory(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        } catch (RuntimeException e) {
            response = new StoryResponse(e.getMessage());
        }

        Assertions.assertEquals(response, failureResponse);
    }

    @Test
    public void followUser_validRequest() {
        FollowUserResponse successResponse = new FollowUserResponse(true, "Success: @AllenAnderson successfully followed @AmyAmes");

        FollowUserRequest request = new FollowUserRequest(user1, user2, testAuthToken);
        FollowActionServiceProxy proxy = new FollowActionServiceProxy();

        FollowUserResponse response;

        try {
            response = proxy.followUser(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        }

        Assertions.assertEquals(response, successResponse);
    }

    @Test
    public void followUser_invalidRequest() {
        FollowUserResponse failureResponse = new FollowUserResponse(false, "Bad Request: no authentication..");

        FollowUserRequest request = new FollowUserRequest(user1, user2, null);
        FollowActionServiceProxy proxy = new FollowActionServiceProxy();

        FollowUserResponse response;

        try {
            response = proxy.followUser(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        }catch (RuntimeException e) {
            response = new FollowUserResponse(false, e.getMessage());
        }

        Assertions.assertEquals(response, failureResponse);
    }

    @Test
    public void unfollowUser_validRequest() {
        UnfollowUserResponse successResponse = new UnfollowUserResponse(true, "Success: @AllenAnderson successfully unfollowed @AmyAmes");

        UnfollowUserRequest request = new UnfollowUserRequest(user1, user2, testAuthToken);
        FollowActionServiceProxy proxy = new FollowActionServiceProxy();

        UnfollowUserResponse response;

        try {
            response = proxy.unfollowUser(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        }

        Assertions.assertEquals(response, successResponse);
    }

    @Test
    public void unfollowUser_invalidRequest() {
        UnfollowUserResponse failureResponse = new UnfollowUserResponse(false, "Bad Request: no authentication..");

        UnfollowUserRequest request = new UnfollowUserRequest(user1, user2, null);
        FollowActionServiceProxy proxy = new FollowActionServiceProxy();

        UnfollowUserResponse response;

        try {
            response = proxy.unfollowUser(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        }catch (RuntimeException e) {
            response = new UnfollowUserResponse(false, e.getMessage());
        }

        Assertions.assertEquals(response, failureResponse);
    }

    @Test
    public void postStatus_validRequest() {
        PostStatusResponse successResponse = new PostStatusResponse(true, "Status successfully posted!");

        PostStatusRequest request = new PostStatusRequest(testUser,"status text", 1607100054, testAuthToken);
        PostStatusServiceProxy proxy = new PostStatusServiceProxy();

        PostStatusResponse response;

        try {
            response = proxy.postStatus(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        }

        Assertions.assertEquals(response, successResponse);
    }

    @Test
    public void postStatus_invalidRequest() {
        PostStatusResponse failureResponse = new PostStatusResponse(false,"Bad Request: date missing");

        PostStatusRequest request = new PostStatusRequest(testUser, "status text", 1607100054, testAuthToken);
        PostStatusServiceProxy proxy = new PostStatusServiceProxy();

        PostStatusResponse response;

        try {
            response = proxy.postStatus(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        }catch (RuntimeException e) {
            response = new PostStatusResponse(false, e.getMessage());
        }

        Assertions.assertEquals(response, failureResponse);
    }

    @Test
    public void getUser_validRequest() {
        GetUserResponse successResponse = new GetUserResponse(new User("Found", "Me",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png"));

        GetUserRequest request = new GetUserRequest(testUser.getAlias());
        GetUserServiceProxy proxy = new GetUserServiceProxy();

        GetUserResponse response;

        try {
            response = proxy.getUser(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        }

        Assertions.assertEquals(response, successResponse);
    }

    @Test
    public void getUser_invalidRequest() {
        GetUserResponse failureResponse = new GetUserResponse("No alias given to find..");

        GetUserRequest request = new GetUserRequest(null);
        GetUserServiceProxy proxy = new GetUserServiceProxy();

        GetUserResponse response;

        try {
            response = proxy.getUser(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        }catch (RuntimeException e) {
            response = new GetUserResponse(e.getMessage());
        }

        Assertions.assertEquals(response, failureResponse);
    }

    @Test
    public void checkUserFollowing_validRequest() {
        CheckUserFollowingResponse successResponse = new CheckUserFollowingResponse(true);

        CheckUserFollowingRequest request = new CheckUserFollowingRequest(user1.getAlias(), user2.getAlias());
        CheckUserFollowingServiceProxy proxy = new CheckUserFollowingServiceProxy();

        CheckUserFollowingResponse response;

        try {
            response = proxy.isUserFollowing(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        }

        Assertions.assertEquals(response, successResponse);
    }

    @Test
    public void checkUserFollowing_invalidRequest() {
        CheckUserFollowingResponse failureResponse = new CheckUserFollowingResponse("User cannot be following self..");

        CheckUserFollowingRequest request = new CheckUserFollowingRequest(user1.getAlias(), user1.getAlias());
        CheckUserFollowingServiceProxy proxy = new CheckUserFollowingServiceProxy();

        CheckUserFollowingResponse response;

        try {
            response = proxy.isUserFollowing(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        } catch (RuntimeException e) {
            response = new CheckUserFollowingResponse(e.getMessage());
        }

        Assertions.assertEquals(response, failureResponse);
    }

    @Test
    public void login_validRequest() {
        LoginResponse successResponse = new LoginResponse("Success! Logged in..", testUser, new AuthToken("will return a UUID-generated authToken"));

        LoginRequest request = new LoginRequest(testUser.getAlias(), "demo password");
        LoginServiceProxy proxy = new LoginServiceProxy();

        LoginResponse response;

        try {
            response = proxy.checkLogin(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        }

        Assertions.assertEquals(response.getUser(), successResponse.getUser());
        Assertions.assertEquals(response.getMessage(), successResponse.getMessage());
    }

    @Test
    public void login_invalidRequest() {
        LoginResponse failureResponse = new LoginResponse("Bad Request: no password given..");

        LoginRequest request = new LoginRequest(testUser.getAlias(), null);
        LoginServiceProxy proxy = new LoginServiceProxy();

        LoginResponse response;

        try {
            response = proxy.checkLogin(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        }catch (RuntimeException e) {
            response = new LoginResponse(e.getMessage());
        }

        Assertions.assertEquals(response, failureResponse);
    }

    @Test
    public void logout_validRequest() {
        LogoutResponse successResponse = new LogoutResponse(true);

        LogoutRequest request = new LogoutRequest(testAuthToken);
        LogoutServiceProxy proxy = new LogoutServiceProxy();

        LogoutResponse response;

        try {
            response = proxy.logout(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        }

        Assertions.assertEquals(response, successResponse);
    }

    @Test
    public void logout_invalidRequest() {
        LogoutResponse failureResponse = new LogoutResponse(false,"Bad Request: no authentication given..");

        LogoutRequest request = new LogoutRequest(null);
        LogoutServiceProxy proxy = new LogoutServiceProxy();

        LogoutResponse response;

        try {
            response = proxy.logout(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        }catch (RuntimeException e) {
            response = new LogoutResponse(false,e.getMessage());
        }

        Assertions.assertEquals(response, failureResponse);
    }

    @Test
    public void register_validRequest() {
        RegisterRequest request = new RegisterRequest("fake user", "myalias", "1234");
        RegisterResponse successResponse = new RegisterResponse(new User(request.getName().split(" ")[0], request.getName().split(" ")[1],
                request.getAlias(), "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png"),
                request.getPassword());

        RegisterServiceProxy proxy = new RegisterServiceProxy();

        RegisterResponse response;

        try {
            response = proxy.registerUser(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        }

        Assertions.assertEquals(response, successResponse);
    }

    @Test
    public void register_invalidRequest() {
        RegisterResponse failureResponse = new RegisterResponse("Failure: Need first and last name to register.");

        RegisterRequest request = new RegisterRequest("fakeuser", "myalias", "1234");
        RegisterServiceProxy proxy = new RegisterServiceProxy();

        RegisterResponse response;

        try {
            response = proxy.registerUser(request);
        } catch (IOException | TweeterRemoteException e) {
            e.printStackTrace();
            response = null;
        }catch (RuntimeException e) {
            response = new RegisterResponse(e.getMessage());
        }

        Assertions.assertEquals(response, failureResponse);

    }















































}
