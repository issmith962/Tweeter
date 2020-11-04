package edu.byu.cs.tweeter.Client.model.services;

import android.os.Build;

import androidx.annotation.RequiresApi;

import edu.byu.cs.tweeter.Client.net.ServerFacade;
import edu.byu.cs.tweeter.Shared.request.FeedRequest;
import edu.byu.cs.tweeter.Shared.response.FeedResponse;

public class FeedService {
    private static FeedService instance;
    private final ServerFacade serverFacade;

    public static FeedService getInstance() {
        if (instance == null) {
            instance = new FeedService();
        }
        return instance;
    }

    private FeedService() {
        serverFacade = new ServerFacade();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public FeedResponse getFeed(FeedRequest request) {
        return serverFacade.getFeed(request);
    }


}
