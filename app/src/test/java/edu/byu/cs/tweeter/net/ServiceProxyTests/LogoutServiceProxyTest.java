package edu.byu.cs.tweeter.net.ServiceProxyTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.LogoutRequest;
import byu.edu.cs.tweeter.shared.response.LogoutResponse;
import edu.byu.cs.tweeter.Client.model.services.LogoutServiceProxy;
import edu.byu.cs.tweeter.Client.net.ServerFacade;

public class LogoutServiceProxyTest {
    private LogoutRequest validRequest;
    private LogoutRequest invalidRequest;

    private LogoutResponse successResponse;
    private LogoutResponse failureResponse;

    private LogoutServiceProxy logoutServiceProxySpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        validRequest = new LogoutRequest(new AuthToken("example token"));
        invalidRequest = new LogoutRequest(null);

        successResponse = new LogoutResponse(true);
        failureResponse = new LogoutResponse(false, "No user logged in..");

        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.logout(validRequest, LogoutServiceProxy.URL_PATH)).thenReturn(successResponse);
        Mockito.when(mockServerFacade.logout(invalidRequest, LogoutServiceProxy.URL_PATH)).thenReturn(failureResponse);

        logoutServiceProxySpy = Mockito.spy(new LogoutServiceProxy());
        Mockito.when(logoutServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void testLogout_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        LogoutResponse response = logoutServiceProxySpy.logout(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testLogout_invalidRequest_returnsNoLogout() throws IOException, TweeterRemoteException {
        LogoutResponse response = logoutServiceProxySpy.logout(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}