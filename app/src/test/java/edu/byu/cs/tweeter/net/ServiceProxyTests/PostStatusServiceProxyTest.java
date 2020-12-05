package edu.byu.cs.tweeter.net.ServiceProxyTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.PostStatusRequest;
import byu.edu.cs.tweeter.shared.response.PostStatusResponse;
import edu.byu.cs.tweeter.Client.model.services.PostStatusServiceProxy;
import edu.byu.cs.tweeter.Client.net.ServerFacade;

public class PostStatusServiceProxyTest {
    private PostStatusRequest validRequest;
    private PostStatusRequest invalidRequest;

    private PostStatusResponse successResponse;
    private PostStatusResponse failureResponse;

    private PostStatusServiceProxy postStatusServiceProxySpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        validRequest = new PostStatusRequest(currentUser, "first status", 1607100123, new AuthToken("example token"));
        invalidRequest = new PostStatusRequest(null, "first status", 1607100456, new AuthToken("example token"));

        successResponse = new PostStatusResponse(true);
        failureResponse = new PostStatusResponse(false, "No user to post for..");

        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.postStatus(validRequest, PostStatusServiceProxy.URL_PATH + "/" + validRequest.getUser().getAlias())).thenReturn(successResponse);

        postStatusServiceProxySpy = Mockito.spy(new PostStatusServiceProxy());
        Mockito.when(postStatusServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testPostStatus_validRequest() throws IOException, TweeterRemoteException {
        PostStatusResponse response = postStatusServiceProxySpy.postStatus(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetPostStatus_invalidRequest() throws IOException, TweeterRemoteException {
        PostStatusResponse response = postStatusServiceProxySpy.postStatus(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
