package edu.byu.cs.tweeter.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import byu.edu.cs.tweeter.server.dao.dummydatadao.FollowDAO;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.request.FollowingRequest;
import byu.edu.cs.tweeter.shared.response.FollowingResponse;

class FollowDAOTest {

    private final User user1 = new User("Daffy", "Duck", "");
    private final User user2 = new User("Fred", "Flintstone", "");
    private final User user3 = new User("Barney", "Rubble", "");
    private final User user4 = new User("Wilma", "Rubble", "");
    private final User user5 = new User("Clint", "Eastwood", "");
    private final User user6 = new User("Mother", "Teresa", "");
    private final User user7 = new User("Harriett", "Hansen", "");
    private final User user8 = new User("Zoe", "Zabriski", "");

    private FollowDAO followDAOSpy;

    @BeforeEach
    void setup() {
        followDAOSpy = Mockito.spy(new FollowDAO());
    }

    @Test
    void testGetFollowees_noFolloweesForUser() {
        List<User> followees = Collections.emptyList();
        Mockito.when(followDAOSpy.getDummyFollowees()).thenReturn(followees);

        FollowingRequest request = new FollowingRequest(user1, 10, null);
        FollowingResponse response = followDAOSpy.getFollowees(request);

        Assertions.assertEquals(0, response.getFollowees().size());
        Assertions.assertFalse(response.hasMorePages());
    }

    @Test
    void testGetFollowees_oneFollowerForUser_limitGreaterThanUsers() {
        List<User> followees = Collections.singletonList(user2);
        Mockito.when(followDAOSpy.getDummyFollowees()).thenReturn(followees);

        FollowingRequest request = new FollowingRequest(user1, 10, null);
        FollowingResponse response = followDAOSpy.getFollowees(request);

        Assertions.assertEquals(1, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user2));
        Assertions.assertFalse(response.hasMorePages());
    }

    @Test
    void testGetFollowees_twoFollowersForUser_limitEqualsUsers() {
        List<User> followees = Arrays.asList(user2, user3);
        Mockito.when(followDAOSpy.getDummyFollowees()).thenReturn(followees);

        FollowingRequest request = new FollowingRequest(user3, 2, null);
        FollowingResponse response = followDAOSpy.getFollowees(request);

        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user2));
        Assertions.assertTrue(response.getFollowees().contains(user3));
        Assertions.assertFalse(response.hasMorePages());
    }

    @Test
    void testGetFollowees_limitLessThanUsers_endsOnPageBoundary() {
        List<User> followees = Arrays.asList(user2, user3, user4, user5, user6, user7);
        Mockito.when(followDAOSpy.getDummyFollowees()).thenReturn(followees);

        FollowingRequest request = new FollowingRequest(user5, 2, null);
        FollowingResponse response = followDAOSpy.getFollowees(request);

        // Verify first page
        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user2));
        Assertions.assertTrue(response.getFollowees().contains(user3));
        Assertions.assertTrue(response.hasMorePages());

        // Get and verify second page
        request = new FollowingRequest(user5, 2, response.getFollowees().get(1));
        response = followDAOSpy.getFollowees(request);

        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user4));
        Assertions.assertTrue(response.getFollowees().contains(user5));
        Assertions.assertTrue(response.hasMorePages());

        // Get and verify third page
        request = new FollowingRequest(user5, 2, response.getFollowees().get(1));
        response = followDAOSpy.getFollowees(request);

        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user6));
        Assertions.assertTrue(response.getFollowees().contains(user7));
        Assertions.assertFalse(response.hasMorePages());
    }


    @Test
    void testGetFollowees_limitLessThanUsers_notEndsOnPageBoundary() {
        List<User> followees = Arrays.asList(user2, user3, user4, user5, user6, user7, user8);
        Mockito.when(followDAOSpy.getDummyFollowees()).thenReturn(followees);

        FollowingRequest request = new FollowingRequest(user6, 2, null);
        FollowingResponse response = followDAOSpy.getFollowees(request);

        // Verify first page
        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user2));
        Assertions.assertTrue(response.getFollowees().contains(user3));
        Assertions.assertTrue(response.hasMorePages());

        // Get and verify second page
        request = new FollowingRequest(user6, 2, response.getFollowees().get(1));
        response = followDAOSpy.getFollowees(request);

        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user4));
        Assertions.assertTrue(response.getFollowees().contains(user5));
        Assertions.assertTrue(response.hasMorePages());

        // Get and verify third page
        request = new FollowingRequest(user6, 2, response.getFollowees().get(1));
        response = followDAOSpy.getFollowees(request);

        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user6));
        Assertions.assertTrue(response.getFollowees().contains(user7));
        Assertions.assertTrue(response.hasMorePages());

        // Get and verify fourth page
        request = new FollowingRequest(user6, 2, response.getFollowees().get(1));
        response = followDAOSpy.getFollowees(request);

        Assertions.assertEquals(1, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user8));
        Assertions.assertFalse(response.hasMorePages());
    }
}
