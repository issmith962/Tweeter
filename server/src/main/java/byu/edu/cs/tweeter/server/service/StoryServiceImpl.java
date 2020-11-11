package byu.edu.cs.tweeter.server.service;

import byu.edu.cs.tweeter.server.dao.StatusDAO;
import byu.edu.cs.tweeter.shared.model.domain.service.StoryService;
import byu.edu.cs.tweeter.shared.request.StoryRequest;
import byu.edu.cs.tweeter.shared.response.StoryResponse;

public class StoryServiceImpl implements StoryService {
    @Override
    public StoryResponse getStory(StoryRequest request) {
        return getStatusDAO().getStory(request);
    }

    StatusDAO getStatusDAO() {
        return new StatusDAO();
    }

}
