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
import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.request.FeedRequest;
import byu.edu.cs.tweeter.shared.response.FeedResponse;


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
}
