package edu.byu.cs.tweeter.net;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.Client.net.ServerFacade;
import byu.edu.cs.tweeter.shared.model.domain.Follow;
import byu.edu.cs.tweeter.shared.model.domain.Status;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.request.CheckUserFollowingRequest;
import byu.edu.cs.tweeter.shared.request.FollowUserRequest;
import byu.edu.cs.tweeter.shared.request.GetUserRequest;
import byu.edu.cs.tweeter.shared.request.UnfollowUserRequest;
import byu.edu.cs.tweeter.shared.response.CheckUserFollowingResponse;
import byu.edu.cs.tweeter.shared.response.FollowUserResponse;
import byu.edu.cs.tweeter.shared.response.GetUserResponse;
import byu.edu.cs.tweeter.shared.response.UnfollowUserResponse;
import edu.byu.cs.tweeter.Client.presenter.VisitorPresenter;

public class TestVisitor implements VisitorPresenter.View {
    VisitorPresenter presenter;
    ServerFacade serverFacade;
    List<Follow> follows;
    Map<User, List<Status>> statusesByUser;
    Map<User, String> passwordsByUser;

    @BeforeEach
    void setup() {
        //View view = Mockito.mock(View.class);
        serverFacade = new ServerFacade();
        presenter = new VisitorPresenter(this);
        follows = new ArrayList<>();
        statusesByUser = new HashMap<>();
        passwordsByUser = new HashMap<>();
        //serverFacade.testInitialize(follows,statusesByUser,passwordsByUser);
    }

    @Test
    void testIsUserFollowingPositive() {
        final User user1 = new User("Daffy", "Duck", "");
        final User user2 = new User("Fred", "Flintstone", "");
        final Follow follow1 = new Follow(user1, user2);
        follows.add(follow1);
        serverFacade.testInitialize(follows,statusesByUser,passwordsByUser);

        CheckUserFollowingRequest request = new CheckUserFollowingRequest(user1, user2.getAlias());
        CheckUserFollowingResponse response = presenter.isUserFollowing(request);

        assertTrue(response.isUserFollowing());
    }

    @Test
    void testIsUserFollowingNegative() {
        final User user1 = new User("Daffy", "Duck", "");
        final User user2 = new User("Fred", "Flintstone", "");
        List<User> allUsers = new ArrayList<>();
        allUsers.add(user1);
        allUsers.add(user2);
        serverFacade.testInitialize(follows,statusesByUser,passwordsByUser, allUsers);

        CheckUserFollowingRequest request = new CheckUserFollowingRequest(user1, user2.getAlias());
        CheckUserFollowingResponse response = presenter.isUserFollowing(request);

        assertFalse(response.isUserFollowing());
    }

    @Test
    void testFollowUserPositive() {
        final User user1 = new User("Daffy", "Duck", "");
        final User user2 = new User("Fred", "Flintstone", "");
        List<User> allUsers = new ArrayList<>();
        allUsers.add(user1);
        allUsers.add(user2);
        serverFacade.testInitialize(follows,statusesByUser,passwordsByUser, allUsers);

        FollowUserRequest request = new FollowUserRequest(user1, user2);
        FollowUserResponse response = presenter.followUser(request);

        assertEquals(response.getMessage(), request.getFollowee().getName() + " followed!");
    }

    @Test
    void testFollowUserNegative() {
        final User user1 = new User("Daffy", "Duck", "");
        final User user2 = new User("Fred", "Flintstone", "");
        final Follow follow1 = new Follow(user1, user2);
        follows.add(follow1);
        serverFacade.testInitialize(follows,statusesByUser,passwordsByUser);

        FollowUserRequest request = new FollowUserRequest(user1, user2);
        FollowUserResponse response = presenter.followUser(request);

        assertEquals(response.getMessage(), "Already Following!");
    }

    @Test
    void testUnfollowUserPositive() {
        final User user1 = new User("Daffy", "Duck", "");
        final User user2 = new User("Fred", "Flintstone", "");
        final Follow follow1 = new Follow(user1, user2);
        follows.add(follow1);
        serverFacade.testInitialize(follows,statusesByUser,passwordsByUser);

        UnfollowUserRequest request = new UnfollowUserRequest(user1, user2);
        UnfollowUserResponse response = presenter.unfollowUser(request);

        assertEquals(response.getMessage(), request.getFollowee().getName() + " unfollowed!");
    }

    @Test
    void testUnfollowUserNegative() {
        final User user1 = new User("Daffy", "Duck", "");
        final User user2 = new User("Fred", "Flintstone", "");
        List<User> allUsers = new ArrayList<>();
        allUsers.add(user1);
        allUsers.add(user2);
        serverFacade.testInitialize(follows,statusesByUser,passwordsByUser, allUsers);

        UnfollowUserRequest request = new UnfollowUserRequest(user1, user2);
        UnfollowUserResponse response = presenter.unfollowUser(request);

        assertEquals(response.getMessage(), "Already not following!");
    }

    @Test
    void testGetUserPositive() {
        final User user1 = new User("Daffy", "Duck", "");
        List<User> allUsers = new ArrayList<>();
        allUsers.add(user1);
        serverFacade.testInitialize(follows,statusesByUser,passwordsByUser, allUsers);

        GetUserRequest request = new GetUserRequest(user1.getAlias());
        GetUserResponse response = presenter.getUser(request);

        assertEquals(response.getUser(), user1);
    }

    @Test
    void testGetUserNegative() {
        final User user1 = new User("Daffy", "Duck", "");
        List<User> allUsers = new ArrayList<>();
        serverFacade.testInitialize(follows,statusesByUser,passwordsByUser, allUsers);

        GetUserRequest request = new GetUserRequest(user1.getAlias());
        GetUserResponse response = presenter.getUser(request);

        assertNull(response.getUser());
    }
}
