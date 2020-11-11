package edu.byu.cs.tweeter.Client.model.services;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.model.domain.service.FeedService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.FeedRequest;
import byu.edu.cs.tweeter.shared.response.FeedResponse;

public class FeedServiceProxy extends Service implements FeedService {
    static final String URL_PATH = "/getfeed";
    public FeedResponse getFeed(FeedRequest request) throws IOException, TweeterRemoteException {
        return getServerFacade().getFeed(request, URL_PATH);
    }


}
