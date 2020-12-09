package byu.edu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import byu.edu.cs.tweeter.shared.model.domain.AuthToken;
import byu.edu.cs.tweeter.shared.net.DataAccessException;

public class AuthTokenDAO {
    private Table table;

    public AuthTokenDAO() {
        getAuthTokenTable();
    }
    private static final String authTokenTableName = "TweeterAuthTokens";

    private static final String tokenAttr = "authToken";
    private static final String aliasAttr = "alias";
    private static final String exptimeAttr = "exptime";

    // DynamoDB Client
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);


    private void getAuthTokenTable() {
        table = dynamoDB.getTable(authTokenTableName);
    }


    //  ----------------------------- DAO ACCESS METHODS ---------------------------------------


    public void createAuthToken(AuthToken authToken, long exptime, String alias) throws DataAccessException {
        // TODO: add the given authToken/alias combo to table.
        try {
            PutItemOutcome outcome = table.putItem(
                    new Item().withPrimaryKey(tokenAttr, authToken.getAuthToken()).withString(aliasAttr, alias)
                    .withLong(exptimeAttr, exptime));
        } catch (Exception e) {
            throw new DataAccessException("Error when adding new authToken to table");
        }
    }

    public void deleteAuthToken(AuthToken authToken) throws DataAccessException {
        // TODO: delete the given authToken (with its alias) from the table
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey(tokenAttr, authToken.getAuthToken()));
        try {
            table.deleteItem(deleteItemSpec);
        } catch (Exception e) {
            throw new DataAccessException("Error when deleting authToken");
        }
    }
    public boolean validateAuthToken(AuthToken authToken, String alias) {
        // TODO: return true if authToken/alias combo is present in table
        //  return false if not present
        GetItemSpec getItemSpec = new GetItemSpec()
                .withPrimaryKey(tokenAttr, authToken.getAuthToken())
                .withConsistentRead(true);
        Item outcome = table.getItem(getItemSpec);
        if (outcome == null) {
            return false;
        }
        else {
            return outcome.getString(aliasAttr).equals(alias);
        }
    }

}

