package edu.byu.cs.tweeter.Client.net;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.CheckUserFollowingRequest;
import byu.edu.cs.tweeter.shared.request.FeedRequest;
import byu.edu.cs.tweeter.shared.request.FollowUserRequest;
import byu.edu.cs.tweeter.shared.request.FolloweeCountRequest;
import byu.edu.cs.tweeter.shared.request.FollowerCountRequest;
import byu.edu.cs.tweeter.shared.request.FollowersRequest;
import byu.edu.cs.tweeter.shared.request.FollowingRequest;
import byu.edu.cs.tweeter.shared.request.GetAllUsersRequest;
import byu.edu.cs.tweeter.shared.request.GetUserRequest;
import byu.edu.cs.tweeter.shared.request.LoginRequest;
import byu.edu.cs.tweeter.shared.request.LogoutRequest;
import byu.edu.cs.tweeter.shared.request.PostStatusRequest;
import byu.edu.cs.tweeter.shared.request.RegisterRequest;
import byu.edu.cs.tweeter.shared.request.StoryRequest;
import byu.edu.cs.tweeter.shared.request.UnfollowUserRequest;
import byu.edu.cs.tweeter.shared.response.CheckUserFollowingResponse;
import byu.edu.cs.tweeter.shared.response.FeedResponse;
import byu.edu.cs.tweeter.shared.response.FollowUserResponse;
import byu.edu.cs.tweeter.shared.response.FolloweeCountResponse;
import byu.edu.cs.tweeter.shared.response.FollowerCountResponse;
import byu.edu.cs.tweeter.shared.response.FollowersResponse;
import byu.edu.cs.tweeter.shared.response.FollowingResponse;
import byu.edu.cs.tweeter.shared.response.GetAllUsersResponse;
import byu.edu.cs.tweeter.shared.response.GetUserResponse;
import byu.edu.cs.tweeter.shared.response.LoginResponse;
import byu.edu.cs.tweeter.shared.response.LogoutResponse;
import byu.edu.cs.tweeter.shared.response.PostStatusResponse;
import byu.edu.cs.tweeter.shared.response.RegisterResponse;
import byu.edu.cs.tweeter.shared.response.StoryResponse;
import byu.edu.cs.tweeter.shared.response.UnfollowUserResponse;

public class ServerFacade{
    private static final String SERVER_URL = "https://y6jsrbbwof.execute-api.us-west-2.amazonaws.com/dev";

    private final ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);

    public LoginResponse checkLogin(LoginRequest request, String urlPath) throws IOException, TweeterRemoteException {
        LoginResponse response = clientCommunicator.doPost(urlPath, request, null, LoginResponse.class);
        if (response.isSuccess()) {
            return response;
        }
        else {
            throw new RuntimeException(response.getMessage());
        }

    }
    public FollowingResponse getFollowees(FollowingRequest request, String urlPath) throws IOException, TweeterRemoteException {
        FollowingResponse response = clientCommunicator.doPost(urlPath, request, null, FollowingResponse.class);
        if (response.isSuccess()) {
            return response;
        }
        else {
            throw new RuntimeException(response.getMessage());
        }
    }
    public FollowersResponse getFollowers(FollowersRequest request, String urlPath) throws IOException, TweeterRemoteException {
        FollowersResponse response = clientCommunicator.doPost(urlPath, request, null, FollowersResponse.class);
        if (response.isSuccess()) {
            return response;
        }
        else {
            throw new RuntimeException(response.getMessage());
        }
    }
    public LogoutResponse logout(LogoutRequest request, String urlPath) throws IOException, TweeterRemoteException {
        LogoutResponse response = clientCommunicator.doPost(urlPath, request, null, LogoutResponse.class);
        if (response.isSuccess()) {
            return response;
        }
        else {
            throw new RuntimeException(response.getMessage());
        }
    }
    public StoryResponse getStory(StoryRequest request, String urlPath) throws IOException, TweeterRemoteException {
        StoryResponse response = clientCommunicator.doPost(urlPath, request, null, StoryResponse.class);
        int a = 5;
        if (response.isSuccess()) {
            return response;
        }
        else {
            throw new RuntimeException(response.getMessage());
        }
    }
    public FeedResponse getFeed(FeedRequest request, String urlPath) throws IOException, TweeterRemoteException {
        FeedResponse response = clientCommunicator.doPost(urlPath, request, null, FeedResponse.class);
        if (response.isSuccess()) {
            return response;
        }
        else {
            throw new RuntimeException(response.getMessage());
        }
    }
    public CheckUserFollowingResponse isUserFollowing(CheckUserFollowingRequest request, String urlPath) throws IOException, TweeterRemoteException {
        CheckUserFollowingResponse response = clientCommunicator.doPost(urlPath, request, null, CheckUserFollowingResponse.class);
        if (response.isSuccess()) {
            return response;
        }
        else {
            throw new RuntimeException(response.getMessage());
        }
    }
    public PostStatusResponse postStatus(PostStatusRequest request, String urlPath) throws IOException, TweeterRemoteException {
        PostStatusResponse response = clientCommunicator.doPost(urlPath, request, null, PostStatusResponse.class);
        int a = 1;
        if (response.isSuccess()) {
            return response;
        }
        else {
            throw new RuntimeException(response.getMessage());
        }
    }
    public UnfollowUserResponse unfollowUser(UnfollowUserRequest request, String urlPath) throws IOException, TweeterRemoteException {
        UnfollowUserResponse response = clientCommunicator.doPost(urlPath, request, null, UnfollowUserResponse.class);
        if (response.isSuccess()) {
            return response;
        }
        else {
            throw new RuntimeException(response.getMessage());
        }
    }
    public FollowUserResponse followUser(FollowUserRequest request, String urlPath) throws IOException, TweeterRemoteException {
        FollowUserResponse response = clientCommunicator.doPost(urlPath, request, null, FollowUserResponse.class);
        if (response.isSuccess()) {
            return response;
        }
        else {
            throw new RuntimeException(response.getMessage());
        }
    }
    public GetAllUsersResponse getAllUsers(GetAllUsersRequest request, String urlPath) throws IOException, TweeterRemoteException {
        GetAllUsersResponse response = clientCommunicator.doPost(urlPath, request, null, GetAllUsersResponse.class);
        if (response.isSuccess()) {
            return response;
        }
        else {
            throw new RuntimeException(response.getMessage());
        }
    }
    public FollowerCountResponse getFollowerCount(FollowerCountRequest request, String urlPath) throws IOException, TweeterRemoteException {
        FollowerCountResponse response = clientCommunicator.doPost(urlPath, request, null, FollowerCountResponse.class);
        if (response.isSuccess()) {
            return response;
        }
        else {
            throw new RuntimeException(response.getMessage());
        }
    }
    public FolloweeCountResponse getFolloweeCount(FolloweeCountRequest request, String urlPath) throws IOException, TweeterRemoteException {
        FolloweeCountResponse response = clientCommunicator.doPost(urlPath, request, null, FolloweeCountResponse.class);
        if (response.isSuccess()) {
            return response;
        }
        else {
            throw new RuntimeException(response.getMessage());
        }
    }
    public RegisterResponse registerUser(RegisterRequest request, String urlPath) throws IOException, TweeterRemoteException {
        RegisterResponse response = clientCommunicator.doPost(urlPath, request, null, RegisterResponse.class);
        if (response.isSuccess()) {
            return response;
        }
        else {
            throw new RuntimeException(response.getMessage());
        }
    }
    public GetUserResponse getUser(GetUserRequest request, String urlPath) throws IOException, TweeterRemoteException {
        GetUserResponse response = clientCommunicator.doPost(urlPath, request, null, GetUserResponse.class);
        int a = 5;
        if (response.isSuccess()) {
            return response;
        }
        else {
            throw new RuntimeException(response.getMessage());
        }
    }
}