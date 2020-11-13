package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import byu.edu.cs.tweeter.server.dao.FollowDAO;
import byu.edu.cs.tweeter.server.service.FollowingServiceImpl;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.request.FollowingRequest;
import byu.edu.cs.tweeter.shared.response.FollowingResponse;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;


public class FollowingServiceImplTest {

    private FollowingRequest request;
    private FollowingResponse expectedResponse;
    private FollowDAO mockFollowDAO;
    private FollowingServiceImpl followingServiceImplSpy;

    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        // Setup a request object to use in the tests
        request = new FollowingRequest(currentUser, 3, null);

        // Setup a mock FollowDAO that will return known responses
        expectedResponse = new FollowingResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
        mockFollowDAO = Mockito.mock(FollowDAO.class);
        Mockito.when(mockFollowDAO.getFollowees(request)).thenReturn(expectedResponse);

        followingServiceImplSpy = Mockito.spy(FollowingServiceImpl.class);
        Mockito.when(followingServiceImplSpy.getFollowDAO()).thenReturn(mockFollowDAO);
    }

    /**
     * Verify that the {@link FollowingServiceImpl#getFollowees(FollowingRequest)}
     * method returns the same result as the {@link FollowDAO} class.
     */
    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowingResponse response = followingServiceImplSpy.getFollowees(request);
        Assertions.assertEquals(expectedResponse, response);
    }
}
