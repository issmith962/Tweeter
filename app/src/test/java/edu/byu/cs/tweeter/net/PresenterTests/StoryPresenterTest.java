package edu.byu.cs.tweeter.net.PresenterTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import byu.edu.cs.tweeter.shared.model.domain.Status;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.model.domain.service.StoryService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.StoryRequest;
import byu.edu.cs.tweeter.shared.response.StoryResponse;
import edu.byu.cs.tweeter.Client.presenter.StoryPresenter;

public class StoryPresenterTest {

    private StoryRequest request;
    private StoryResponse response;
    private StoryService mockStoryService;
    private StoryPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);
        Status currentUserStatus1 = new Status(currentUser, 1607100123, "status text");
        Status currentUserStatus2 = new Status(currentUser, 1607100345, "status text");
        Status currentUserStatus3 = new Status(currentUser, 1607100456, "status text");


        request = new StoryRequest(currentUser, 10, null);
        response = new StoryResponse(Arrays.asList(currentUserStatus1, currentUserStatus2, currentUserStatus3), false);

        mockStoryService = Mockito.mock(StoryService.class);
        Mockito.when(mockStoryService.getStory(request)).thenReturn(response);

        presenter = Mockito.spy(new StoryPresenter(new StoryPresenter.View() {}));
        Mockito.when(presenter.getStoryService()).thenReturn(mockStoryService);
    }

    @Test
    public void testGetStory_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockStoryService.getStory(request)).thenReturn(response);
        Assertions.assertEquals(response, presenter.getStory(request));
    }

    @Test
    public void testGetStory_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockStoryService.getStory(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> presenter.getStory(request));
    }


}
