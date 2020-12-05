package edu.byu.cs.tweeter.net.ServiceProxyTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import byu.edu.cs.tweeter.shared.model.domain.Status;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.StoryRequest;
import byu.edu.cs.tweeter.shared.response.StoryResponse;
import edu.byu.cs.tweeter.Client.model.services.StoryServiceProxy;
import edu.byu.cs.tweeter.Client.net.ServerFacade;

public class StoryServiceProxyTest {

    private StoryRequest validRequest;
    private StoryRequest invalidRequest;

    private StoryResponse successResponse;
    private StoryResponse failureResponse;

    private StoryServiceProxy storyServiceProxySpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);
        Status currentUserStatus1 = new Status(currentUser, 1607100054, "status text");
        Status currentUserStatus2 = new Status(currentUser, 1607100344, "status text");
        Status currentUserStatus3 = new Status(currentUser, 1607100367, "status text");

        validRequest = new StoryRequest(currentUser, 10, null);
        invalidRequest = new StoryRequest(null, 10, null);

        successResponse = new StoryResponse(Arrays.asList(currentUserStatus1, currentUserStatus2, currentUserStatus3), false);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getStory(validRequest, StoryServiceProxy.URL_PATH + "/" + validRequest.getUser().getAlias())).thenReturn(successResponse);

        failureResponse = new StoryResponse("Failure: User information invalid..");
        //This call should fail in the service proxy and not even make it to the facade.
        //Mockito.when(mockServerFacade.getStory(invalidRequest, StoryServiceProxy.URL_PATH)).thenReturn(failureResponse);

        storyServiceProxySpy = Mockito.spy(new StoryServiceProxy());
        Mockito.when(storyServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testGetStory_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        StoryResponse response = storyServiceProxySpy.getStory(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetStory_invalidRequest_returnsNoStory() throws IOException, TweeterRemoteException {
        StoryResponse response = storyServiceProxySpy.getStory(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }

}
