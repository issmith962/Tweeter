package byu.edu.cs.tweeter.shared.service;


import byu.edu.cs.tweeter.shared.request.PostStatusRequest;
import byu.edu.cs.tweeter.shared.response.PostStatusResponse;

public interface PostStatusService {
    public PostStatusResponse postStatus(PostStatusRequest request);
}

