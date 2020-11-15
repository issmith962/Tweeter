package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import byu.edu.cs.tweeter.server.dao.AuthTokenDAO;
import byu.edu.cs.tweeter.server.service.LogoutServiceImpl;
import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.LogoutRequest;
import byu.edu.cs.tweeter.shared.response.LogoutResponse;

public class LogoutServiceImplTest {
    private LogoutRequest validRequest;
    private LogoutRequest invalidRequest;

    private LogoutResponse successResponse;
    private LogoutResponse failureResponse;

    private LogoutServiceImpl logoutServiceImplSpy;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        validRequest = new LogoutRequest(new AuthToken("example token"));
        invalidRequest = new LogoutRequest(null);

        successResponse = new LogoutResponse(true);
        failureResponse = new LogoutResponse(false, "Bad Request: no authentication given..");

        AuthTokenDAO mockAuthTokenDAO = Mockito.mock(AuthTokenDAO.class);

        logoutServiceImplSpy = Mockito.spy(new LogoutServiceImpl());

        Mockito.when(logoutServiceImplSpy.getAuthTokenDAO()).thenReturn(mockAuthTokenDAO);
    }

    @Test
    public void testLogout_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        LogoutResponse response = logoutServiceImplSpy.logout(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void testLogout_invalidRequest_returnsNoLogout() throws IOException, TweeterRemoteException {
        LogoutResponse response = logoutServiceImplSpy.logout(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
