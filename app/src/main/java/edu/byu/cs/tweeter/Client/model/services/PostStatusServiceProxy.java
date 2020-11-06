package edu.byu.cs.tweeter.Client.model.services;

import android.os.Build;

import androidx.annotation.RequiresApi;

import byu.edu.cs.tweeter.shared.model.domain.service.PostStatusService;
import byu.edu.cs.tweeter.shared.request.PostStatusRequest;
import byu.edu.cs.tweeter.shared.response.PostStatusResponse;

public class PostStatusServiceProxy extends Service implements PostStatusService {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public PostStatusResponse postStatus(PostStatusRequest request) {
        return getServerFacade().postStatus(request);
    }
}

