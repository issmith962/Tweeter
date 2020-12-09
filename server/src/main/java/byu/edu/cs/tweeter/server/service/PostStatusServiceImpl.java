package byu.edu.cs.tweeter.server.service;

import byu.edu.cs.tweeter.server.dao.AuthTokenDAO;
import byu.edu.cs.tweeter.server.dao.DAOHelperFunctions;
import byu.edu.cs.tweeter.server.dao.PostUpdateFeedMessagesSQSManager;
import byu.edu.cs.tweeter.server.dao.StoryDAO;
import byu.edu.cs.tweeter.server.dao.UpdateFeedSQSManager;
import byu.edu.cs.tweeter.shared.model.domain.Status;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.model.domain.service.PostStatusService;
import byu.edu.cs.tweeter.shared.net.DataAccessException;
import byu.edu.cs.tweeter.shared.request.FollowersRequest;
import byu.edu.cs.tweeter.shared.request.PostStatusRequest;
import byu.edu.cs.tweeter.shared.request.PostUpdateFeedMessagesRequest;
import byu.edu.cs.tweeter.shared.request.UpdateFeedRequest;
import byu.edu.cs.tweeter.shared.response.FollowersResponse;
import byu.edu.cs.tweeter.shared.response.PostStatusResponse;

public class PostStatusServiceImpl implements PostStatusService {
    @Override
    public PostStatusResponse postStatus(PostStatusRequest request) {
        if (request.getUser() == null) {
            return new PostStatusResponse(false, "Bad Request: no user given..");
        }
        if (request.getAuthToken() == null) {
            return new PostStatusResponse(false, "Bad Request: no authentication");
        }
        if (request.getPostingTimestamp() == 0) {
            return new PostStatusResponse(false, "Bad Request: timestamp is zero");
        }
        if (request.getNewStatus() == null) {
            return new PostStatusResponse(false, "Bad Request: new post cannot be empty");
        }
        if (request.getNewStatus().equals("")) {
            return new PostStatusResponse(false, "Bad Request: new post cannot be empty");
        }

        boolean correctAuthToken = getAuthTokenDAO().validateAuthToken(request.getAuthToken(), request.getUser().getAlias());
        if (!correctAuthToken) {
            return new PostStatusResponse(false,"Failure: Login expired! Please log in again..");
        }

        try {
            getStoryDAO().postStatusToStory(request.getUser().getAlias(),
                        DAOHelperFunctions.createDatePlusPostedBy(request.getUser().getAlias(), request.getPostingTimestamp()),
                        request.getNewStatus());
        } catch (DataAccessException e) {
            return new PostStatusResponse(false, "Error in posting status to story..");
        }

        System.out.println("passed here 1...");
        // Create PostUpdateFeedMessagesHandler request
        PostUpdateFeedMessagesRequest postUpdateFeedMessagesRequest = new PostUpdateFeedMessagesRequest(
                new Status(request.getUser(), request.getPostingTimestamp(), request.getNewStatus())
        );
        System.out.println("passed here 2...");
        // Send message To SQSPostStatusQueue with the request
        PostUpdateFeedMessagesSQSManager.sendUpdateFeedMessage(postUpdateFeedMessagesRequest);
        System.out.println("passed here 3...");

        return new PostStatusResponse(true);
    }

    public void sendUpdateFeedMessage(PostUpdateFeedMessagesRequest request) {
        User lastFollower = null;
        User followee = request.getStatus().getUser();
        int limit = 25;

        while (true) {
            FollowersResponse followersResponse = new FollowerServiceImpl().getFollowers(new FollowersRequest(followee, limit, lastFollower));
            if (followersResponse.getFollowers() == null) {
                break;
            }
            else if (followersResponse.getFollowers().size() == 0) {
                break;
            }
            else {
                UpdateFeedRequest updateFeedRequest = new UpdateFeedRequest(request.getStatus(), followersResponse.getFollowers());
                UpdateFeedSQSManager.updateFeed(updateFeedRequest);
            }
            if (!followersResponse.hasMorePages()) {
                break;
            }
        }
    }

    public AuthTokenDAO getAuthTokenDAO() {
        return new AuthTokenDAO();
    }

    private StoryDAO getStoryDAO() {
        return new StoryDAO();
    }
}
