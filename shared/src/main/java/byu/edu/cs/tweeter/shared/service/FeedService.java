package byu.edu.cs.tweeter.shared.service;


import byu.edu.cs.tweeter.shared.request.FeedRequest;
import byu.edu.cs.tweeter.shared.response.FeedResponse;

public interface FeedService {
    public FeedResponse getFeed(FeedRequest request);


}
