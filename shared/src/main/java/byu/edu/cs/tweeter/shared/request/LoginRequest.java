package byu.edu.cs.tweeter.shared.request;

/**
 * Contains the alias and password input by the user to attempt login.
 */
public class LoginRequest {
    private String alias;
    private String password;

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

    public LoginRequest() {
        alias = null;
        password = null;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}