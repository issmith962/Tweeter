package byu.edu.cs.tweeter.server.service;

import byu.edu.cs.tweeter.server.dao.StatusDAO;
import byu.edu.cs.tweeter.shared.model.domain.service.StoryService;
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

        return getStatusDAO().getStory(request);
    }

    public StatusDAO getStatusDAO() {
        return new StatusDAO();
    }

}
