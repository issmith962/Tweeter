package edu.byu.cs.tweeter.server.dao.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import byu.edu.cs.tweeter.server.dao.AuthTokenDAO;
import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.net.DataAccessException;

public class AuthTokenDAOITest {
    private AuthTokenDAO dao = new AuthTokenDAO();
    private AuthToken tok1 = new AuthToken("token");
    private String alias1 = "testing";
    private long exptime1 = 1607610806; // just the current time when I was writing the test..

    @Test
    public void testAuthTokenDAOMethods() {
        /* createAuthToken_test */
        try {
            dao.createAuthToken(tok1, exptime1, alias1);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        /* validateAuthToken_test_validToken */
        boolean validToken = dao.validateAuthToken(tok1, alias1);
        Assertions.assertTrue(validToken);

        /* deleteAuthToken_test */
        try {
            dao.deleteAuthToken(tok1);
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }

        /* validateAuthToken_test_invalidToken */
        boolean invalidToken = dao.validateAuthToken(tok1, alias1);
        Assertions.assertFalse(invalidToken);
    }
}
