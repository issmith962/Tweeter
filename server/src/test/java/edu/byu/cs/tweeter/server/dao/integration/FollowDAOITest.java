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
        Assertions.assertEquals(0, dao.getFollowers(new FollowersRequest(u1, 10, null)).getFollowers().size());

        // getFollowees_validRequest (followees == 0)
        Assertions.assertEquals(0, dao.getFollowees(new FollowingRequest(u2, 10, null)).getFollowees().size());

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
        Assertions.assertEquals(1, followers.size());
        Assertions.assertEquals(u2.getAlias(), followers.get(0).getAlias());
        Assertions.assertEquals(u2.getFirstName(), followers.get(0).getFirstName());
        Assertions.assertEquals(u2.getLastName(), followers.get(0).getLastName());
        Assertions.assertEquals(u2.getImageUrl(), followers.get(0).getImageUrl());

        // getFollowees_validRequest (followees > 0)
        List<User> followees = dao.getFollowees(new FollowingRequest(u2, 10, null)).getFollowees();
        Assertions.assertEquals(followees.size(), 1);
        Assertions.assertEquals(u1.getAlias(), followees.get(0).getAlias());
        Assertions.assertEquals(u1.getFirstName(), followees.get(0).getFirstName());
        Assertions.assertEquals(u1.getLastName(), followees.get(0).getLastName());
        Assertions.assertEquals(u1.getImageUrl(), followees.get(0).getImageUrl());

        // removeFollow
        try {
            dao.removeFollow(u1, u2);
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }
    }

}
