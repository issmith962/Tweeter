package byu.edu.cs.tweeter.server.dao;

import byu.edu.cs.tweeter.shared.model.domain.AuthToken;

public class AuthTokenDAO {
    public void createAuthToken(AuthToken authToken, String alias) {
        // TODO: add the given authToken/alias combo to table.
    }

    public void deleteAuthToken(AuthToken authToken) {
        // TODO: delete the given authToken (with its alias) from the table
    }
    public boolean validateAuthToken(AuthToken authToken, String alias) {
        // TODO: return true if authToken/alias combo is present in table
        //  return false if not present
        return true;
    }
}
