package byu.edu.cs.tweeter.shared.model.domain.service;


import java.io.IOException;

import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import byu.edu.cs.tweeter.shared.request.PostStatusRequest;
import byu.edu.cs.tweeter.shared.response.PostStatusResponse;

public interface PostStatusService {
    public PostStatusResponse postStatus(PostStatusRequest request) throws IOException, TweeterRemoteException;
}

