package edu.byu.cs.tweeter.net.PresenterTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.model.domain.service.LoginService;
import byu.edu.cs.tweeter.shared.model.domain.service.RegisterService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.LoginRequest;
import byu.edu.cs.tweeter.shared.request.RegisterRequest;
import byu.edu.cs.tweeter.shared.response.LoginResponse;
import byu.edu.cs.tweeter.shared.response.RegisterResponse;
import edu.byu.cs.tweeter.Client.presenter.LoginPresenter;

public class LoginPresenterTest {
    private LoginRequest loginRequest;
    private LoginResponse loginResponse;
    private LoginService mockLoginService;

    private RegisterRequest registerRequest;
    private RegisterResponse registerResponse;
    private RegisterService mockRegisterService;

    private LoginPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        loginRequest = new LoginRequest("alias", "password");
        loginResponse = new LoginResponse("Success: User logged in", currentUser, new AuthToken("example token"));
        mockLoginService = Mockito.mock(LoginService.class);
        Mockito.when(mockLoginService.checkLogin(loginRequest)).thenReturn(loginResponse);

        registerRequest = new RegisterRequest("first last", "firstlast", "1234");
        registerResponse = new RegisterResponse(currentUser, "1234");
        mockRegisterService = Mockito.mock(RegisterService.class);
        Mockito.when(mockRegisterService.registerUser(registerRequest)).thenReturn(registerResponse);

        presenter = Mockito.spy(new LoginPresenter(new LoginPresenter.View() {}));
        
        Mockito.when(presenter.getLoginService()).thenReturn(mockLoginService);
        Mockito.when(presenter.getRegisterService()).thenReturn(mockRegisterService);
    }

    @Test
    public void testCheckLogin_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockLoginService.checkLogin(loginRequest)).thenReturn(loginResponse);
        Assertions.assertEquals(loginResponse, presenter.checkLogin(loginRequest));
    }

    @Test
    public void testCheckLogin_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockLoginService.checkLogin(loginRequest)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> presenter.checkLogin(loginRequest));
    }

    @Test
    public void testRegister_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockRegisterService.registerUser(registerRequest)).thenReturn(registerResponse);
        Assertions.assertEquals(registerResponse, presenter.registerUser(registerRequest));
    }

    @Test
    public void testRegister_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockRegisterService.registerUser(registerRequest)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> presenter.registerUser(registerRequest));
    }



}
