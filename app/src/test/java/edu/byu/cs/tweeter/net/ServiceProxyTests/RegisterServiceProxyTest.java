package edu.byu.cs.tweeter.net.ServiceProxyTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.RegisterRequest;
import byu.edu.cs.tweeter.shared.response.RegisterResponse;
import edu.byu.cs.tweeter.Client.model.services.RegisterServiceProxy;
import edu.byu.cs.tweeter.Client.net.ServerFacade;

public class RegisterServiceProxyTest {
    private RegisterRequest validRequest;
    private RegisterRequest invalidRequest;

    private RegisterResponse successResponse;
    private RegisterResponse failureResponse;

    private RegisterServiceProxy registerServiceProxySpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        validRequest = new RegisterRequest("first last", "firstlast", "1234");
        invalidRequest = new RegisterRequest(null, "firstlast", "1234");

        successResponse = new RegisterResponse(currentUser, "1234");
        failureResponse = new RegisterResponse("Registration Error: No name provided..");

        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.registerUser(validRequest, RegisterServiceProxy.URL_PATH)).thenReturn(successResponse);
        Mockito.when(mockServerFacade.registerUser(invalidRequest, RegisterServiceProxy.URL_PATH)).thenReturn(failureResponse);

        registerServiceProxySpy = Mockito.spy(new RegisterServiceProxy());
        Mockito.when(registerServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testRegister_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        RegisterResponse response = registerServiceProxySpy.registerUser(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testRegister_invalidRequest_returnsNoRegister() throws IOException, TweeterRemoteException {
        RegisterResponse response = registerServiceProxySpy.registerUser(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
