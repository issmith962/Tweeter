package edu.byu.cs.tweeter.server.dao.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import byu.edu.cs.tweeter.server.dao.DAOHelpers.DAOHelperFunctions;
import byu.edu.cs.tweeter.server.dao.StoryDAO;
import byu.edu.cs.tweeter.shared.model.domain.Status;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.DataAccessException;
import byu.edu.cs.tweeter.shared.response.StoryResponse;

public class StoryDAOITest {
    private StoryDAO dao = new StoryDAO();

    private String alias = "storyteller";
    private String firstName = "Storied";
    private String lastName = "User";
    private String imageUrl = "https://cs340-tweeter-profile-pic-storage.s3.amazonaws.com/TestUserProfPic.png";

    private User user = new User(firstName, lastName, alias, imageUrl);
    private Status status = new Status(user, 1607610806, "my first status");

    @Test
    public void testStoryDAOMethods() {
        // getStory_(story == 0 statuses)
        StoryResponse result1;
        try {
            result1 = dao.getStory(user, 10, null);
        } catch (DataAccessException e) {
            result1 = null;
            e.printStackTrace();
        }
        Assertions.assertNotNull(result1);
        Assertions.assertNotNull(result1.getStory());
        Assertions.assertEquals(result1.getStory().size(), 0);
        Assertions.assertFalse(result1.hasMorePages());

        // postStatusToStory
        String datePlusPostedBy = DAOHelperFunctions.createDatePlusPostedBy(alias, status.getTimestamp());
        try {
            dao.postStatusToStory(status.getUser().getAlias(), datePlusPostedBy, status.getStatus_text());
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }

        // getStory (story == 1 status)
        StoryResponse result2;
        try {
            result2 = dao.getStory(user, 10, null);
        } catch (DataAccessException e) {
            result2 = null;
            e.printStackTrace();
        }
        Assertions.assertNotNull(result2);
        Assertions.assertNotNull(result2.getStory());
        Assertions.assertEquals(result2.getStory().size(), 1);
        Assertions.assertFalse(result2.hasMorePages());
        Status s = result2.getStory().get(0);
        Assertions.assertEquals(s.getStatus_text(), status.getStatus_text());
        Assertions.assertEquals(s.getTimestamp(), status.getTimestamp());
        Assertions.assertEquals(s.getUser().getAlias(), status.getUser().getAlias());
        Assertions.assertEquals(s.getUser().getFirstName(), status.getUser().getFirstName());
        Assertions.assertEquals(s.getUser().getLastName(), status.getUser().getLastName());
        Assertions.assertEquals(s.getUser().getImageUrl(), status.getUser().getImageUrl());

        // remove status
        try {
            dao.removeStatus(alias, datePlusPostedBy);
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }
    }
}
