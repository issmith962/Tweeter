package edu.byu.cs.tweeter.server.dao.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import byu.edu.cs.tweeter.server.dao.UserDAO;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.DataAccessException;

public class UserDAOITest {
    private UserDAO dao = new UserDAO();

    private String alias = "reguser";
    private String firstName = "Reg";
    private String lastName = "Ister";
    private String imageUrl = "https://cs340-tweeter-profile-pic-storage.s3.amazonaws.com/reguserProfPic.png";
    private String password = "test_password";
    private String securePassword = "64967b252ed30f5bda877e4c26c43d805ecdf0484a53b5fb9eb59a5b61bee721";
    private String salt = "99h1K5Zk3k0rw1ACBWihQw==";
    private String imageBytes = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABAQMAAAAl21bKAAAAA1BMVEUAAACnej3aAAAAAXRSTlMAQObYZgAAAApJREFUCNdjYAAAAAIAAeIhvDMAAAAASUVORK5CYII=";

    private User user = new User(firstName, lastName, alias, imageUrl);

    @Test
    public void testUserDAOMethods() {
        // isAliasTaken_invalidAlias
        Assertions.assertFalse(dao.isAliasTaken(alias));

        // findUserByAlias_invalidAlias
        Assertions.assertNull(dao.findUserByAlias(alias));

        // validateLogin_invalidLogin
        Assertions.assertFalse(dao.validateLogin(alias, password));

        // storeProfPic_invalidPic
        Assertions.assertNull(dao.storeProfPic(alias, ""));

        // storeProfPic_validPic
        Assertions.assertEquals(dao.storeProfPic(alias, imageBytes), imageUrl);

        // registerNewUser
        try {
            dao.registerNewUser(alias, firstName, lastName, securePassword, salt, imageUrl);
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }

        // validateLogin_validLogin
        Assertions.assertTrue(dao.validateLogin(alias, password));

        // isAliasTaken_validAlias
        Assertions.assertTrue(dao.isAliasTaken(alias));

        // findUserByAlias_validAlias
        User u = dao.findUserByAlias(alias);
        Assertions.assertEquals(alias, u.getAlias());
        Assertions.assertEquals(firstName, u.getFirstName());
        Assertions.assertEquals(lastName, u.getLastName());
        Assertions.assertEquals(imageUrl, u.getImageUrl());

        // getFollowerCount_default
        Assertions.assertEquals(dao.getFollowerCount(alias), 0);

        // getFolloweeCount_default
        Assertions.assertEquals(dao.getFolloweeCount(alias), 0);

        // incrementFollowerCount
        try {
            dao.incrementFollowerCount(alias);
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }

        // incrementFolloweeCount
        try {
            dao.incrementFolloweeCount(alias);
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }

        // getFollowerCount_defaultPlusOne
        Assertions.assertEquals(dao.getFollowerCount(alias), 1);

        // getFolloweeCount_defaultPlusOne
        Assertions.assertEquals(dao.getFolloweeCount(alias), 1);

        // incrementFollowerCount
        try {
            dao.incrementFollowerCount(alias);
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }

        // incrementFolloweeCount
        try {
            dao.incrementFolloweeCount(alias);
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }

        // getFollowerCount_defaultPlusTwo
        Assertions.assertEquals(dao.getFollowerCount(alias), 2);

        // getFolloweeCount_defaultPlusTwo
        Assertions.assertEquals(dao.getFolloweeCount(alias), 2);

        // decrementFollowerCount
        try {
            dao.decrementFollowerCount(alias);
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }

        // decrementFolloweeCount
        try {
            dao.decrementFolloweeCount(alias);
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }

        // getFollowerCount_defaultPlusOne
        Assertions.assertEquals(dao.getFollowerCount(alias), 1);

        // getFolloweeCount_defaultPlusOne
        Assertions.assertEquals(dao.getFolloweeCount(alias), 1);


        // removeUser
        try {
            dao.removeUser(alias);
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail("Problem removing the test user from the database...");
        }
    }
}
