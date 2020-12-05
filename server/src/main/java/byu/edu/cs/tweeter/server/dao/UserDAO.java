package byu.edu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

import byu.edu.cs.tweeter.server.encryption.SaltedSHAHashing;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.DataAccessException;

public class UserDAO {
    private Table table;

    public UserDAO() {
        getUserTable();

    }
    private static final String userTableName = "TweeterUsers";

    private static final String aliasAttr = "alias";

    private static final String firstNameAttr = "firstName";
    private static final String lastNameAttr = "lastName";
    private static final String imageUrlAttr = "imageUrl";
    private static final String securePasswordAttr = "securePassword";
    private static final String saltAttr = "salt";

    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    private static AmazonS3 s3 = AmazonS3ClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();

    private static String profPicBucket = "cs340-tweeter-profile-pic-storage";
    private static String imageUrlStem = "https://" + profPicBucket + ".s3.amazonaws.com/";


    //  ----------------------------- DAO ACCESS METHODS ---------------------------------------


    public boolean isAliasTaken(String alias) {
        GetItemSpec getItemSpec = new GetItemSpec()
                .withPrimaryKey(aliasAttr, alias)
                .withConsistentRead(true);
        Item outcome = table.getItem(getItemSpec);
        if (outcome != null) {
            return true;
        }
        else {
            return false;
        }
    }

    /*
    Stores the given imageData string as an image in the s3 bucket.
    Returns the url to the profile picture in its new location.
     */
    public String storeProfPic(String alias, String imageData) {
        byte[] profPic = Base64.getDecoder().decode(imageData);
        InputStream stream = new ByteArrayInputStream(profPic);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(profPic.length);
        metadata.setContentType("image/png");
        String imageFileNameEnding = "ProfPic.png";
        s3.putObject(new PutObjectRequest(profPicBucket,
                alias + imageFileNameEnding, stream, metadata));
        return imageUrlStem + alias + imageFileNameEnding;
    }

    public void registerNewUser(String alias, String firstName, String lastName, String securePassword, String salt, String imageUrl) throws DataAccessException {
        try {
            PutItemOutcome addUserOutcome = table.putItem(
                    new Item().withPrimaryKey(aliasAttr, alias)
                            .withString(firstNameAttr, firstName)
                            .withString(lastNameAttr, lastName)
                            .withString(securePasswordAttr, securePassword)
                            .withString(saltAttr, salt)
                            .withString(imageUrlAttr, imageUrl));
        } catch (Exception e) {
            throw new DataAccessException("Error when adding new user to table");
        }
    }

    public User findUserByAlias(String alias) {
        GetItemSpec getItemSpec  = new GetItemSpec()
                .withPrimaryKey(aliasAttr, alias)
                .withConsistentRead(true);
        Item outcome = table.getItem(getItemSpec);
        if (outcome == null) {
            return null;
        }
        else {
            String firstName = outcome.getString(firstNameAttr);
            String lastName = outcome.getString(lastNameAttr);
            String imageUrl = outcome.getString(imageUrlAttr);
            return new User(firstName, lastName, alias, imageUrl);
        }
    }

    public boolean validateLogin(String alias, String password) {
        // check login credentials again User table, return true if they match
        GetItemSpec getItemSpec  = new GetItemSpec()
                .withPrimaryKey(aliasAttr, alias)
                .withConsistentRead(true);
        Item outcome = table.getItem(getItemSpec);
        if (outcome == null) {
            return false;
        }
        else {
            String hashedPassword = SaltedSHAHashing.getSecurePassword(password, outcome.getString(saltAttr));
            return hashedPassword.equals(outcome.getString(securePasswordAttr));
        }
    }

    private void getUserTable() {
        table = dynamoDB.getTable(userTableName);
    }





}
