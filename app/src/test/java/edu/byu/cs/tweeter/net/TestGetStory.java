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
import edu.byu.cs.tweeter.Shared.request.StoryRequest;
import edu.byu.cs.tweeter.Shared.response.StoryResponse;
import edu.byu.cs.tweeter.Client.presenter.StoryPresenter;

public class TestGetStory  implements StoryPresenter.View {
    private StoryPresenter presenter;
    private ServerFacade serverFacade;
    private List<Follow> follows;
    private Map<User,List<Status>> statusesByUser;
    private Map<User,String> passwordsByUser;
    @BeforeEach
    void setup() {
        //View view = Mockito.mock(View.class);
        serverFacade = new ServerFacade();
        presenter = new StoryPresenter(this);
        follows = new ArrayList<>();
        statusesByUser = new HashMap<>();
        passwordsByUser = new HashMap<>();
        //serverFacade.testInitialize(follows,statusesByUser,passwordsByUser);
    }

    @Test
    void testGetStatusPositive() {
        final User user1 = new User("Daffy", "Duck", "");
        final Status status1 = new Status(user1, "02/01/2020", "What a great day!");
        final Status status2 = new Status(user1, "11/21/2019", "I'm happy");
        final Status status3 = new Status(user1, "07/19/2009", "I hate Djokovic");
        List<User> allUsers = new ArrayList<>();
        allUsers.add(user1);
        List<Status> statuses = new ArrayList<>();
        statuses.add(status1);
        statuses.add(status2);
        statuses.add(status3);
        statusesByUser.put(user1, statuses);
        serverFacade.testInitialize(follows, statusesByUser, passwordsByUser, allUsers);

        StoryRequest request = new StoryRequest(user1, 10, null, );
        StoryResponse response = presenter.getStory(request);
        int a = 2; 
        assertTrue(response.getStory().contains(status1));
        assertTrue(response.getStory().contains(status2));
        assertTrue(response.getStory().contains(status3));
    }

    @Test
    void testGetStatusNegative() {
        final User user1 = new User("Daffy", "Duck", "");
        List<User> allUsers = new ArrayList<>();
        allUsers.add(user1);
        serverFacade.testInitialize(follows, statusesByUser, passwordsByUser, allUsers);
        StoryRequest request = new StoryRequest(user1, 10, null);
        StoryResponse response = presenter.getStory(request);

        assertTrue(response.getStory().isEmpty());
    }
}
