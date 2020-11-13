package edu.byu.cs.tweeter.net;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.util.Arrays;

import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.request.FollowingRequest;
import byu.edu.cs.tweeter.shared.response.FollowersResponse;
import byu.edu.cs.tweeter.shared.response.FollowingResponse;
import edu.byu.cs.tweeter.Client.model.services.FollowingServiceProxy;
import edu.byu.cs.tweeter.Client.net.ServerFacade;

public class FollowingServiceProxyTest {

    private FollowingRequest validRequest;
    private FollowingRequest invalidRequest;

    private FollowingResponse successResponse;
    private FollowingResponse failureResponse;

    private FollowingServiceProxy followingServiceProxy;

    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName,", "LastName", null);
        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        validRequest = new FollowingRequest(currentUser, 3, null);
        invalidRequest = new FollowingRequest(null, 0, null);

        successResponse = new FollowingResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getFollowees(validRequest, FollowingServiceProxy.)
    }










}

