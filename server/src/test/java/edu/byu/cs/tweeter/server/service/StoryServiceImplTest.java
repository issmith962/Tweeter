package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import byu.edu.cs.tweeter.server.dao.StatusDAO;
import byu.edu.cs.tweeter.server.service.StoryServiceImpl;
import byu.edu.cs.tweeter.shared.model.domain.Status;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.StoryRequest;
import byu.edu.cs.tweeter.shared.response.StoryResponse;

public class StoryServiceImplTest {

    private StoryRequest validRequest;
    private StoryRequest invalidRequest;

    private StoryResponse successResponse;
    private StoryResponse failureResponse;

    private StoryServiceImpl storyServiceImplSpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);
        Status currentUserStatus1 = new Status(currentUser, "01/01/2001", "status text");
        Status currentUserStatus2 = new Status(currentUser, "02/02/2002", "status text");
        Status currentUserStatus3 = new Status(currentUser, "03/03/2003", "status text");

        validRequest = new StoryRequest(currentUser, 10, null);
        invalidRequest = new StoryRequest(null, 10, null);

        successResponse = new StoryResponse(Arrays.asList(currentUserStatus1, currentUserStatus2, currentUserStatus3), false);
        failureResponse = new StoryResponse("Bad Request: no user given..");

        StatusDAO mockStatusDAO = Mockito.mock(StatusDAO.class);
        Mockito.when(mockStatusDAO.getStory(validRequest)).thenReturn(successResponse);

        storyServiceImplSpy = Mockito.spy(new StoryServiceImpl());
        Mockito.when(storyServiceImplSpy.getStatusDAO()).thenReturn(mockStatusDAO);
    }

    @Test
    public void testGetStory_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        StoryResponse response = storyServiceImplSpy.getStory(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetStory_invalidRequest_returnsNoStory() throws IOException, TweeterRemoteException {
        StoryResponse response = storyServiceImplSpy.getStory(invalidRequest);
        Assertions.assertEquals(failureResponse.getMessage(), response.getMessage());
    }
}
