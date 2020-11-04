package edu.byu.cs.tweeter.Shared.request;

/**
 * Contains the alias and password input by the user to attempt login.
 */
public class LoginRequest {
    private final String alias;
    private final String password;

    /**
     * Creates an instance.
     *
     * @param alias the alias or handle input by the user.
     * @param password the password input by the user.
     */
    public LoginRequest(String alias, String password) {
        this.alias = alias;
        this.password = password;
    }

    /**
     * Returns the alias this request is checking to validate login information.
     * @return the alias to be checked.
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Returns the password this request is checking to validate login information.
     * @return the password to be checked.
     */
    public String getPassword() {
        return password;
    }
}