package byu.edu.cs.tweeter.server.service;

import byu.edu.cs.tweeter.server.dao.AuthTokenDAO;
import byu.edu.cs.tweeter.server.dao.DAOHelperFunctions;
import byu.edu.cs.tweeter.server.dao.StoryDAO;
import byu.edu.cs.tweeter.shared.model.domain.service.PostStatusService;
import byu.edu.cs.tweeter.shared.net.DataAccessException;
import byu.edu.cs.tweeter.shared.request.PostStatusRequest;
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


        // TODO: Get list of followees
        // TODO: Create UpdateFeedRequest
        // TODO: Send UpdateFeedRequest to SQS queue for lambda:UpdateFeed processing


        return new PostStatusResponse(true);
    }

    public AuthTokenDAO getAuthTokenDAO() {
        return new AuthTokenDAO();
    }

    private StoryDAO getStoryDAO() {
        return new StoryDAO();
    }
}
