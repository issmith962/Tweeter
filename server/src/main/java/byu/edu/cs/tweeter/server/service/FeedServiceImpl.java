package byu.edu.cs.tweeter.server.service;

import byu.edu.cs.tweeter.server.dao.AuthTokenDAO;
import byu.edu.cs.tweeter.server.dao.DAOHelpers.DAOHelperFunctions;
import byu.edu.cs.tweeter.server.dao.FeedDAO;
import byu.edu.cs.tweeter.shared.model.domain.service.FeedService;
import byu.edu.cs.tweeter.shared.net.DataAccessException;
import byu.edu.cs.tweeter.shared.request.FeedRequest;
import byu.edu.cs.tweeter.shared.request.UpdateFeedRequest;
import byu.edu.cs.tweeter.shared.response.FeedResponse;

public class FeedServiceImpl implements FeedService {
    @Override
    public FeedResponse getFeed(FeedRequest request) {
        if (request.getAuthToken() == null) {
            return new FeedResponse("Bad Request: No authentication..");
        }
        if (request.getUser() == null) {
            return new FeedResponse("Bad Request: No user given..");
        }
        if (request.getLimit() == 0) {
            return new FeedResponse("Bad Request: No statuses requested..");
        }

        boolean correctAuthToken = getAuthTokenDAO().validateAuthToken(request.getAuthToken(), request.getUser().getAlias());
        if (!correctAuthToken) {
            return new FeedResponse("Failure: Login expired! Please log in again..");
        }

        // DynamoDB implementation with FeedDAO

        String last_datePlusPostedBy;
        if (request.getLastStatus() != null) {
            last_datePlusPostedBy = DAOHelperFunctions.createDatePlusPostedBy(
                    request.getLastStatus().getUser().getAlias(), request.getLastStatus().getTimestamp());
        } else {
            last_datePlusPostedBy = null;
        }
        try {
            return getFeedDAO().getFeed(request.getUser(), request.getLimit(), last_datePlusPostedBy);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new FeedResponse("Error in retrieving feed from database");
        }
    }

    public void updateFeed(UpdateFeedRequest request) {
        String datePlusPostedBy = DAOHelperFunctions.createDatePlusPostedBy(
                request.getStatus().getUser().getAlias(), request.getStatus().getTimestamp());
        getFeedDAO().updateFeed(request.getFollowees(), request.getStatus(), datePlusPostedBy);
    }

    public AuthTokenDAO getAuthTokenDAO() {
        return new AuthTokenDAO();
    }

    public FeedDAO getFeedDAO() {
        return new FeedDAO();
    }

}
