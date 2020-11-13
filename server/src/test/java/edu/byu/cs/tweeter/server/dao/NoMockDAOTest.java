package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.asserts.Assertion;

import byu.edu.cs.tweeter.server.dao.AuthTokenDAO;
import byu.edu.cs.tweeter.server.dao.FollowDAO;
import byu.edu.cs.tweeter.server.dao.StatusDAO;
import byu.edu.cs.tweeter.server.dao.UserDAO;
import byu.edu.cs.tweeter.server.service.FeedServiceImpl;
import byu.edu.cs.tweeter.server.service.FollowActionServiceImpl;
import byu.edu.cs.tweeter.server.service.PostStatusServiceImpl;
import byu.edu.cs.tweeter.server.service.StoryServiceImpl;
import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.request.FeedRequest;
import byu.edu.cs.tweeter.shared.request.PostStatusRequest;
import byu.edu.cs.tweeter.shared.request.StoryRequest;
import byu.edu.cs.tweeter.shared.request.UnfollowUserRequest;
import byu.edu.cs.tweeter.shared.response.FeedResponse;
import byu.edu.cs.tweeter.shared.response.PostStatusResponse;
import byu.edu.cs.tweeter.shared.response.StoryResponse;
import byu.edu.cs.tweeter.shared.response.UnfollowUserResponse;


/*
This class is specifically for debugging the lambdas and DAOs without running
AWS debugging programs like SAM, etc. This is purely to simulate how any server
program responds to certain requests so I can run through it with the debugger.
 */
public class NoMockDAOTest {

    private User testUser;

    @BeforeEach
    public void setup() {
        testUser = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
    }

    @Test
    public void noLastUserFeedTest() {
        FeedRequest request = new FeedRequest(testUser, 10, null, new AuthToken("random auth token"));
        FeedResponse response = new FeedServiceImpl().getFeed(request);
        // breakpoint line to look at response
        int a = 5;

    }

    @Test
    public void unfollowUserTest_userIsFollowed() {
        UnfollowUserRequest request = new UnfollowUserRequest(new User(), new User(), new AuthToken("asdfjkl;"));
        UnfollowUserResponse response = new FollowActionServiceImpl().unfollowUser(request);
        // breakpoint line to look at response
        int a = 5;
    }

    @Test
    public void postStatus() {
        PostStatusRequest request = new PostStatusRequest(testUser, "hello", "11/13/2020", new AuthToken("asdfjk;"));
        PostStatusResponse response = new PostStatusServiceImpl().postStatus(request);
        // breakpoint line to look at response
        int a = 5;
    }

    @Test
    public void postStatus_plusGetStory() {
        PostStatusRequest request = new PostStatusRequest(testUser, "hello", "11/13/2020", new AuthToken("asdfjk;"));
        PostStatusResponse response = new PostStatusServiceImpl().postStatus(request);
        // breakpoint line to look at response
        int a = 5;
        StoryRequest request1 = new StoryRequest(testUser, 10, null);
        StoryResponse response1 = new StoryServiceImpl().getStory(request1);
        // breakpoint line to look at response
        a = 10;
    }
}
