package edu.byu.cs.tweeter.net;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.Client.net.ServerFacade;
import byu.edu.cs.tweeter.shared.domain.Follow;
import byu.edu.cs.tweeter.shared.domain.Status;
import byu.edu.cs.tweeter.shared.domain.User;
import byu.edu.cs.tweeter.shared.request.GetAllUsersRequest;
import byu.edu.cs.tweeter.shared.response.GetAllUsersResponse;
import edu.byu.cs.tweeter.Client.presenter.SearchPresenter;

public class TestGetAllUsers  implements SearchPresenter.View {
    SearchPresenter presenter;
    ServerFacade serverFacade;
    List<Follow> follows;
    Map<User, List<Status>> statusesByUser;
    Map<User, String> passwordsByUser;

    @BeforeEach
    void setup() {
        //View view = Mockito.mock(View.class);
        serverFacade = new ServerFacade();
        presenter = new SearchPresenter(this);
        follows = new ArrayList<>();
        statusesByUser = new HashMap<>();
        passwordsByUser = new HashMap<>();
        //serverFacade.testInitialize(follows,statusesByUser,passwordsByUser);
    }

    @AfterEach
    void tearDown() {
        serverFacade = null;
        presenter = null;
        follows = null;
        statusesByUser = null;
        passwordsByUser = null;
    }
    @Test
    void testGetAllUsersPositive() {
        final User user1 = new User("Daffy", "Duck", "");
        final User user2 = new User("Fred", "Flintstone", "");
        final User user3 = new User("Barney", "Rubble", "");
        final User user4 = new User("Wilma", "Rubble", "");
        List<User> allUsers = new ArrayList<>();
        allUsers.add(user1);
        allUsers.add(user2);
        allUsers.add(user3);
        allUsers.add(user4);
        serverFacade.testInitialize(follows,statusesByUser,passwordsByUser,allUsers);

        GetAllUsersResponse response = presenter.getAllUsers(new GetAllUsersRequest());

        assertEquals(response.getAllUsers().size(), 4);
        assertTrue(response.getAllUsers().contains(user1));
        assertTrue(response.getAllUsers().contains(user2));
        assertTrue(response.getAllUsers().contains(user3));
        assertTrue(response.getAllUsers().contains(user4));

        serverFacade = null;
    }



    @Test
    void testGetAllUsersNegative() {
        serverFacade = new ServerFacade();
        serverFacade.testInitialize(follows,statusesByUser,passwordsByUser, new ArrayList<User>());

        GetAllUsersResponse response = presenter.getAllUsers(new GetAllUsersRequest());

        assertEquals(response.getAllUsers().size(), 0);

    }


}
