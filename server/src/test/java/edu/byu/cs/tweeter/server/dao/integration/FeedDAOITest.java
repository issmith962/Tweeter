package edu.byu.cs.tweeter.server.dao.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import byu.edu.cs.tweeter.server.dao.DAOHelpers.DAOHelperFunctions;
import byu.edu.cs.tweeter.server.dao.FeedDAO;
import byu.edu.cs.tweeter.shared.model.domain.Status;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.DataAccessException;
import byu.edu.cs.tweeter.shared.response.FeedResponse;

public class FeedDAOITest {
    private FeedDAO dao = new FeedDAO();

    private String imageUrl = "https://cs340-tweeter-profile-pic-storage.s3.amazonaws.com/TestUserProfPic.png";
    private long datePosted = 1607610806;
    private String statusText = "Testing...Testing...1 2 3...";
    private User u1 = new User("User1", "One", "user1", imageUrl);
    private User u2 = new User("User2", "Two", "user2", imageUrl);
    private User u3 = new User("User3", "Three", "user3", imageUrl);

    @Test
    public void testFeedDAOMethods() {
        try {
            FeedResponse response = dao.getFeed(u2, 10, null);
            Assertions.assertEquals(0, response.getFeed().size());
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }

        try {
            FeedResponse response = dao.getFeed(u3, 10, null);
            Assertions.assertEquals(0, response.getFeed().size());
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }

        // updateFeed
        List<User> followees = new ArrayList<>();
        followees.add(u2);
        followees.add(u3);

        Status status = new Status(u1, datePosted, statusText);
        String datePlusPostedBy = DAOHelperFunctions.createDatePlusPostedBy(u1.getAlias(), status.getTimestamp());
        dao.updateFeed(followees, status, datePlusPostedBy);

        // getFeed (feed size == 1)

        // u2's feed
        try {
            FeedResponse response = dao.getFeed(u2, 10, null);
            List<Status> feed = response.getFeed();
            Assertions.assertEquals(1, feed.size());
            Assertions.assertEquals(u1.getAlias(), feed.get(0).getUser().getAlias());
            Assertions.assertEquals(u1.getFirstName(), feed.get(0).getUser().getFirstName());
            Assertions.assertEquals(u1.getLastName(), feed.get(0).getUser().getLastName());
            Assertions.assertEquals(u1.getImageUrl(), feed.get(0).getUser().getImageUrl());
            Assertions.assertEquals(datePosted, feed.get(0).getTimestamp());
            Assertions.assertEquals(statusText, feed.get(0).getStatus_text());

        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }

        // u3's feed
        try {
            FeedResponse response = dao.getFeed(u3, 10, null);
            List<Status> feed = response.getFeed();
            Assertions.assertEquals(1, feed.size());
            Assertions.assertEquals(u1.getAlias(), feed.get(0).getUser().getAlias());
            Assertions.assertEquals(u1.getFirstName(), feed.get(0).getUser().getFirstName());
            Assertions.assertEquals(u1.getLastName(), feed.get(0).getUser().getLastName());
            Assertions.assertEquals(u1.getImageUrl(), feed.get(0).getUser().getImageUrl());
            Assertions.assertEquals(datePosted, feed.get(0).getTimestamp());
            Assertions.assertEquals(statusText, feed.get(0).getStatus_text());
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }

        // Remove status from feeds
        try {
            dao.removeStatus(u2.getAlias(), datePlusPostedBy);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        try {
            dao.removeStatus(u3.getAlias(), datePlusPostedBy);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

    }
}
