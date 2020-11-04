package edu.byu.cs.tweeter.net;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.Client.net.ServerFacade;
import edu.byu.cs.tweeter.Shared.domain.Follow;
import edu.byu.cs.tweeter.Shared.domain.Status;
import edu.byu.cs.tweeter.Shared.domain.User;
import edu.byu.cs.tweeter.Shared.request.FeedRequest;
import edu.byu.cs.tweeter.Shared.response.FeedResponse;
import edu.byu.cs.tweeter.Client.presenter.FeedPresenter;

public class TestFeed implements FeedPresenter.View {
    FeedPresenter presenter;
    ServerFacade serverFacade;
    List<Follow> follows;
    Map<User,List<Status>> statusesByUser;
    Map<User,String> passwordsByUser;
    @BeforeEach
    void setup() {
        //View view = Mockito.mock(View.class);
        serverFacade = new ServerFacade();
        presenter = new FeedPresenter(this);
        follows = new ArrayList<>();
        statusesByUser = new HashMap<>();
        passwordsByUser = new HashMap<>();
        //serverFacade.testInitialize(follows,statusesByUser,passwordsByUser);
    }

    @Test
    void testGetFeedPositive() {
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
        final Status status2 = new Status(user2, "02/01/2020", "What a great day!");
        final Status status3 = new Status(user3, "11/21/2019", "I'm happy");
        final Status status4 = new Status(user4, "07/19/2009", "I hate Djokovic");
        statusesByUser.put(user2, new ArrayList<>());
        statusesByUser.put(user3, new ArrayList<>());
        statusesByUser.put(user4, new ArrayList<>());
        statusesByUser.get(user2).add(status2);
        statusesByUser.get(user3).add(status3);
        statusesByUser.get(user4).add(status4);
        serverFacade.testInitialize(follows,statusesByUser,passwordsByUser);

        FeedRequest request = new FeedRequest(user1, 10, null);
        FeedResponse response = presenter.getFeed(request);

        assertEquals(response.getFeed().size(), 3);
        assertTrue(response.getFeed().contains(status2));
        assertTrue(response.getFeed().contains(status3));
        assertTrue(response.getFeed().contains(status4));
    }

    @Test
    void testGetFeedNegative() {
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
        final Status status2 = new Status(user2, "02/01/2020", "What a great day!");
        final Status status3 = new Status(user3, "11/21/2019", "I'm happy");
        final Status status4 = new Status(user4, "07/19/2009", "I hate Djokovic");
        statusesByUser.put(user2, new ArrayList<>());
        statusesByUser.put(user3, new ArrayList<>());
        statusesByUser.put(user4, new ArrayList<>());
        statusesByUser.get(user2).add(status2);
        statusesByUser.get(user3).add(status3);
        statusesByUser.get(user4).add(status4);
        serverFacade.testInitialize(follows,statusesByUser,passwordsByUser);

        FeedRequest request = new FeedRequest(null, 10, null);
        assertThrows(AssertionError.class, () -> presenter.getFeed(request));
    }
}
