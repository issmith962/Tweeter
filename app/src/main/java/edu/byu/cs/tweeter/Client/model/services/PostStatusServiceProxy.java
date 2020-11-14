package edu.byu.cs.tweeter.Client.model.services;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.model.domain.service.PostStatusService;
import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.PostStatusRequest;
import byu.edu.cs.tweeter.shared.response.PostStatusResponse;

public class PostStatusServiceProxy extends Service implements PostStatusService {
    public static final String URL_PATH = "/poststatus";

    public PostStatusResponse postStatus(PostStatusRequest request) throws IOException, TweeterRemoteException {
        if (request.getUser() == null) {
            return new PostStatusResponse(false, "No user to post for..");
        }
        String param_alias = "/" + request.getUser().getAlias();
        String newUrlPath = URL_PATH + param_alias;

        return getServerFacade().postStatus(request, newUrlPath);
    }
}

