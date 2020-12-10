package edu.byu.cs.tweeter.server.dao.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import byu.edu.cs.tweeter.server.dao.FollowDAO;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.DataAccessException;
import byu.edu.cs.tweeter.shared.request.FollowersRequest;
import byu.edu.cs.tweeter.shared.request.FollowingRequest;

public class FollowDAOITest {
    private FollowDAO dao = new FollowDAO();
    private String imageUrl = "https://cs340-tweeter-profile-pic-storage.s3.amazonaws.com/TestUserProfPic.png";
    private User u1 = new User("User1", "One", "user1", imageUrl);
    private User u2 = new User("User2", "Two", "user2", imageUrl);


    @Test
    public void testFollowDAOMethods() {
        // REMOVE THE FOLLOW IN CASE IT ALREADY EXISTS
        try {
            dao.removeFollow(u1, u2);
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }

        // getFollowers_validRequest (followers == 0)
        Assertions.assertEquals(dao.getFollowers(new FollowersRequest(u1, 10, null)).getFollowers().size(), 0);

        // getFollowees_validRequest (followees == 0)
        Assertions.assertEquals(dao.getFollowees(new FollowingRequest(u2, 10, null)).getFollowees().size(), 0);

        // checkFollow_false
        Assertions.assertFalse(dao.checkFollow(u1.getAlias(), u2.getAlias()));

        // addFollow
        try {
            dao.addFollow(u1, u2);
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }

        // checkFollow_true
        Assertions.assertTrue(dao.checkFollow(u1.getAlias(), u2.getAlias()));

        // getFollowers_validRequest (followers > 0)
        List<User> followers = dao.getFollowers(new FollowersRequest(u1, 10, null)).getFollowers();
        Assertions.assertEquals(followers.size(), 1);
        Assertions.assertEquals(followers.get(0).getAlias(), u2.getAlias());
        Assertions.assertEquals(followers.get(0).getFirstName(), u2.getFirstName());
        Assertions.assertEquals(followers.get(0).getLastName(), u2.getLastName());
        Assertions.assertEquals(followers.get(0).getImageUrl(), u2.getImageUrl());

        // getFollowees_validRequest (followees > 0)
        List<User> followees = dao.getFollowees(new FollowingRequest(u2, 10, null)).getFollowees();
        Assertions.assertEquals(followees.size(), 1);
        Assertions.assertEquals(followees.get(0).getAlias(), u1.getAlias());
        Assertions.assertEquals(followees.get(0).getFirstName(), u1.getFirstName());
        Assertions.assertEquals(followees.get(0).getLastName(), u1.getLastName());
        Assertions.assertEquals(followees.get(0).getImageUrl(), u1.getImageUrl());

        // removeFollow
        try {
            dao.removeFollow(u1, u2);
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }
    }


}
