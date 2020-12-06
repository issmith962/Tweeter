package byu.edu.cs.tweeter.server.service;

import byu.edu.cs.tweeter.server.dao.DAOHelperFunctions;
import byu.edu.cs.tweeter.server.dao.StoryDAO;
import byu.edu.cs.tweeter.shared.model.domain.service.StoryService;
import byu.edu.cs.tweeter.shared.net.DataAccessException;
import byu.edu.cs.tweeter.shared.request.StoryRequest;
import byu.edu.cs.tweeter.shared.response.StoryResponse;

public class StoryServiceImpl implements StoryService {
    @Override
    public StoryResponse getStory(StoryRequest request) {
        if (request.getUser() == null) {
            return new StoryResponse("Bad Request: no user given..");
        }
        if (request.getLimit() == 0) {
            return new StoryResponse("Bad Request: no statuses requested..");
        }
        String last_datePlusPostedBy;
        if (request.getLastStatus() != null) {
            last_datePlusPostedBy = DAOHelperFunctions.createDatePlusPostedBy(
                    request.getLastStatus().getUser().getAlias(), request.getLastStatus().getTimestamp());
        }
        else {
            last_datePlusPostedBy = null;
        }
        try {
            StoryResponse response = getStoryDAO().getStory(request.getUser(), request.getLimit(), last_datePlusPostedBy);
            return response;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new StoryResponse("Error in retrieving story from database..");
        }
    }

    private StoryDAO getStoryDAO() {
        return new StoryDAO();
    }

}



