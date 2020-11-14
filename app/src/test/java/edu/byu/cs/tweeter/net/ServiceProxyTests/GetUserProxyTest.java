package edu.byu.cs.tweeter.net.ServiceProxyTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.GetUserRequest;
import byu.edu.cs.tweeter.shared.response.GetUserResponse;
import edu.byu.cs.tweeter.Client.model.services.GetUserServiceProxy;
import edu.byu.cs.tweeter.Client.net.ServerFacade;

public class GetUserProxyTest {
    private GetUserRequest validRequest;
    private GetUserRequest invalidRequest;

    private GetUserResponse successResponse;
    private GetUserResponse failureResponse;

    private GetUserServiceProxy getUserServiceProxySpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        validRequest = new GetUserRequest("example alias");
        invalidRequest = new GetUserRequest(null);

        successResponse = new GetUserResponse(currentUser);
        failureResponse = new GetUserResponse("No alias given to find.."); 

        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getUser(validRequest, GetUserServiceProxy.URL_PATH + "/" + validRequest.getAlias())).thenReturn(successResponse);

        getUserServiceProxySpy = Mockito.spy(new GetUserServiceProxy());
        Mockito.when(getUserServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testGetUser_validRequest() throws IOException, TweeterRemoteException {
        GetUserResponse response = getUserServiceProxySpy.getUser(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testGetGetUser_invalidRequest() throws IOException, TweeterRemoteException {
        GetUserResponse response = getUserServiceProxySpy.getUser(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
    
}
