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
import byu.edu.cs.tweeter.shared.request.FollowingRequest;
import byu.edu.cs.tweeter.shared.response.CheckUserFollowingResponse;
import byu.edu.cs.tweeter.shared.response.FollowingResponse;
import edu.byu.cs.tweeter.Client.presenter.FollowingPresenter;

public class TestFollowing implements FollowingPresenter.View {
    FollowingPresenter presenter;
    ServerFacade serverFacade;
    List<Follow> follows;
    Map<User,List< Status >> statusesByUser;
    Map<User,String> passwordsByUser;
    @BeforeEach
    void setup() {
        //View view = Mockito.mock(View.class);
        serverFacade = new ServerFacade();
        presenter = new FollowingPresenter(this);
        follows = new ArrayList<>();
        statusesByUser = new HashMap<>();
        passwordsByUser = new HashMap<>();
        //serverFacade.testInitialize(follows,statusesByUser,passwordsByUser);
    }

    @Test
    void testCheckUserFollowingPositive() {
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
    void testCheckUserFollowingNegative() {
        final User user1 = new User("Daffy", "Duck", "");
        final User user2 = new User("Fred", "Flintstone", "");
        final Follow follow1 = new Follow(user2, user1);
        follows.add(follow1);
        serverFacade.testInitialize(follows,statusesByUser,passwordsByUser);

        CheckUserFollowingRequest request = new CheckUserFollowingRequest(user1, user2.getAlias());
        CheckUserFollowingResponse response = presenter.isUserFollowing(request);
        assertFalse(response.isUserFollowing());
    }

    @Test
    void testGetFollowingPositive() {
        final User user1 = new User("Daffy", "Duck", "");
        final User user2 = new User("Fred", "Flintstone", "");
        final User user3 = new User("Barney", "Rubble", ""); 
        final User user4 = new User("Wilma", "Rubble", "");
        final Follow follow1 = new Follow(user1, user2);
        final Follow follow2 = new Follow(user1, user3);
        final Follow follow3 = new Follow(user1, user4);
        follows.add(follow1);
        follows.add(follow2);
        follows.add(follow3);
        serverFacade.testInitialize(follows,statusesByUser,passwordsByUser);

        FollowingRequest request = new FollowingRequest(user1, 10, null);
        FollowingResponse response = presenter.getFollowing(request);
        assertEquals(response.getFollowees().size(), 3);
        assertTrue(response.getFollowees().contains(user2));
        assertTrue(response.getFollowees().contains(user3));
        assertTrue(response.getFollowees().contains(user4));
    }

    @Test
    void testGetFollowingNegative() {
        final User user1 = new User("Daffy", "Duck", "");
        final User user2 = new User("Fred", "Flintstone", "");
        final User user3 = new User("Barney", "Rubble", "");
        final User user4 = new User("Wilma", "Rubble", "");
        final Follow follow1 = new Follow(user2, user1);
        final Follow follow2 = new Follow(user3, user1);
        final Follow follow3 = new Follow(user4, user1);
        follows.add(follow1);
        follows.add(follow2);
        follows.add(follow3);
        serverFacade.testInitialize(follows,statusesByUser,passwordsByUser);

        FollowingRequest request = new FollowingRequest(null, 10, null);
        assertThrows(AssertionError.class, () -> presenter.getFollowing(request));
    }
}
