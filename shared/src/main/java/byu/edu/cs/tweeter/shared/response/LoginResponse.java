package byu.edu.cs.tweeter.shared.response;

import byu.edu.cs.tweeter.shared.model.domain.AuthToken;

/**
 * A response for a {@Link LoginRequest}.
 */
public class LoginResponse extends Response {
    private String alias;
    private String password;
    private AuthToken authToken;
    private String firstName;
    private String lastName;
    private String imageURL;

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param message the success message
     * @param alias the user's alias
     * @param password the user's password
     * @param authToken the user's authtoken
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param imageURL the user's profile picture URL
     */
    public LoginResponse(String message, String alias, String password, AuthToken authToken, String firstName, String lastName, String imageURL) {
        super(true, message);
        this.alias = alias;
        this.password = password;
        this.authToken = authToken;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageURL = imageURL;
    }
    public LoginResponse(String message, String alias, String password, AuthToken authToken, String firstName, String lastName) {
        super(true, message);
        this.alias = alias;
        this.password = password;
        this.authToken = authToken;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageURL = null;
    }


    /**
     * Creates a response indicating that the corresponding request was unsuccessful.
     *
     * @param message the message indicating the login was successful.
     */
    public LoginResponse(String message) {
        super(false, message);
    }

    public String getAlias() {
        return alias;
    }
    public String getPassword() {
        return password;
    }
    public AuthToken getAuthToken() {
        return authToken;
    }
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getImageURL() {
        return imageURL;
    }
}