package edu.byu.cs.tweeter.model.services;

import android.os.Build;

import androidx.annotation.RequiresApi;

import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.net.response.PostStatusResponse;

public class PostStatusService {
    private static PostStatusService instance;
    private final ServerFacade serverFacade;

    public static PostStatusService getInstance() {
        if (instance == null) {
            instance = new PostStatusService();
        }
        return instance;
    }

    private PostStatusService() {
        serverFacade = new ServerFacade();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public PostStatusResponse postStatus(PostStatusRequest request) {
        return serverFacade.postStatus(request);
    }
}

