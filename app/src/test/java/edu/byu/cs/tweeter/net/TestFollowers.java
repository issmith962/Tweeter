package edu.byu.cs.tweeter.net;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.CheckUserFollowingRequest;
import edu.byu.cs.tweeter.net.request.FollowersRequest;
import edu.byu.cs.tweeter.net.request.FollowingRequest;
import edu.byu.cs.tweeter.net.response.CheckUserFollowingResponse;
import edu.byu.cs.tweeter.net.response.FollowersResponse;
import edu.byu.cs.tweeter.net.response.FollowingResponse;
import edu.byu.cs.tweeter.presenter.FollowersPresenter;
import edu.byu.cs.tweeter.presenter.FollowingPresenter;

public class TestFollowers implements FollowersPresenter.View {
    FollowersPresenter presenter;
    ServerFacade serverFacade;
    List<Follow> follows;
    Map<User,List< Status >> statusesByUser;
    Map<User,String> passwordsByUser;
    @BeforeEach
    void setup() {
        //View view = Mockito.mock(View.class);
        serverFacade = new ServerFacade();
        presenter = new FollowersPresenter(this);
        follows = new ArrayList<>();
        statusesByUser = new HashMap<>();
        passwordsByUser = new HashMap<>();
        //serverFacade.testInitialize(follows,statusesByUser,passwordsByUser);
    }

    @Test
    void testGetFollowersPositive() {
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

        FollowersRequest request = new FollowersRequest(user1, 10, null);
        FollowersResponse response = presenter.getFollowers(request);
        assertEquals(response.getFollowers().size(), 3);
        assertTrue(response.getFollowers().contains(user2));
        assertTrue(response.getFollowers().contains(user3));
        assertTrue(response.getFollowers().contains(user4));
    }

    @Test
    void testGetFollowersNegative() {
        final User user1 = new User("Daffy", "Duck", "");
        final User user2 = new User("Fred", "Flintstone", "");
        final User user3 = new User("Barney", "Rubble", "");
        final User user4 = new User("Wilma", "Rubble", "");
        final Follow follow1 = new Follow(user1, user2);
        final Follow follow2 = new Follow(user1, user2);
        final Follow follow3 = new Follow(user1, user2);
        follows.add(follow1);
        follows.add(follow2);
        follows.add(follow3);
        serverFacade.testInitialize(follows,statusesByUser,passwordsByUser);

        FollowersRequest request = new FollowersRequest(null, 10, null);
        assertThrows(AssertionError.class, () -> presenter.getFollowers(request));
    }
}
