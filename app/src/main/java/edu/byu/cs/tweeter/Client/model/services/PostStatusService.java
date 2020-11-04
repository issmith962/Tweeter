package edu.byu.cs.tweeter.Client.model.services;

import android.os.Build;

import androidx.annotation.RequiresApi;

import edu.byu.cs.tweeter.Client.net.ServerFacade;
import byu.edu.cs.tweeter.shared.request.PostStatusRequest;
import byu.edu.cs.tweeter.shared.response.PostStatusResponse;

public class  PostStatusService {
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

