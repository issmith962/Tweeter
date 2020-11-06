package edu.byu.cs.tweeter.net;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.Client.net.ServerFacade;
import byu.edu.cs.tweeter.shared.model.domain.Follow;
import byu.edu.cs.tweeter.shared.model.domain.Status;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.request.PostStatusRequest;
import byu.edu.cs.tweeter.shared.response.PostStatusResponse;
import edu.byu.cs.tweeter.Client.presenter.MainPresenter;

public class TestPostStatus implements MainPresenter.View {
    MainPresenter presenter;
    ServerFacade serverFacade;
    List<Follow> follows;
    Map<User,List<Status>> statusesByUser;
    Map<User,String> passwordsByUser;

    @BeforeEach
    void setup() {
        //View view = Mockito.mock(View.class);
        serverFacade = new ServerFacade();
        presenter = new MainPresenter(this);
        follows = new ArrayList<>();
        statusesByUser = new HashMap<>();
        passwordsByUser = new HashMap<>();
        //serverFacade.testInitialize(follows,statusesByUser,passwordsByUser);
    }

    @Test
    void testPostStatusPositive() {
        final User user1 = new User("Daffy", "Duck", "");
        List<User> allUsers = new ArrayList<>();
        allUsers.add(user1);
        serverFacade.testInitialize(follows,statusesByUser,passwordsByUser,allUsers);

        PostStatusRequest request = new PostStatusRequest(user1, "hello world", "01/01/2020");
        PostStatusResponse response = presenter.postStatus(request);

        assertEquals(response.getMessage(), "Status successfully posted!");
    }

    @Test
    void testPostStatusNegative() {
        final User user1 = new User("Daffy", "Duck", "");
        List<User> allUsers = new ArrayList<>();
        allUsers.add(user1);
        serverFacade.testInitialize(follows,statusesByUser,passwordsByUser,allUsers);

        PostStatusRequest request = new PostStatusRequest(user1, "", "01/01/2020");
        PostStatusResponse response = presenter.postStatus(request);

        assertEquals(response.getMessage(), "Status posting failed...");
    }
}
