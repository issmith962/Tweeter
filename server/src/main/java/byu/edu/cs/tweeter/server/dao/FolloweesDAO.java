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
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.DataAccessException;
import byu.edu.cs.tweeter.shared.request.FollowingRequest;
import byu.edu.cs.tweeter.shared.response.FollowingResponse;

public class FolloweesDAO {
    private Table table;

    public FolloweesDAO() {
        getFolloweesTable();
    }

    private static final String followeesTableName = "TweeterFollowees";

    private static final String followerAliasAttr = "followerAlias";
    private static final String followeeAliasAttr = "followeeAlias";

    private static final String followeeFirstNameAttr = "followeeFirstName";
    private static final String followeeLastNameAttr = "followeeLastName";
    private static final String followeeImageUrlAttr = "followeeImageUrl";

    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
    private void getFolloweesTable() {
        table = dynamoDB.getTable(followeesTableName);
    }

    //  ----------------------------- DAO ACCESS METHODS ---------------------------------------

    public void addFollow(User follower, User followee) throws DataAccessException {
        try {
            PutItemOutcome outcome = table.putItem(
                    new Item().withPrimaryKey(followerAliasAttr, follower.getAlias(), followeeAliasAttr, followee.getAlias())
                            .withString(followeeFirstNameAttr, followee.getFirstName())
                            .withString(followeeLastNameAttr, followee.getLastName())
                            .withString(followeeImageUrlAttr, followee.getImageUrl()));
        } catch (Exception e) {
            throw new DataAccessException("Error when adding new follow relationship to followees table");
        }
    }

    public void removeFollow(User follower, User followee) throws DataAccessException {
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey(followerAliasAttr, follower.getAlias(), followeeAliasAttr, followee.getAlias()));
        try {
            table.deleteItem(deleteItemSpec);
        } catch (Exception e) {
            throw new DataAccessException("Error when removing follow relationship from followees table");
        }
    }

    public boolean checkFollow(String followerAlias, String followeeAlias) {
        GetItemSpec getItemSpec = new GetItemSpec()
                .withPrimaryKey(followerAliasAttr, followerAlias, followeeAliasAttr, followeeAlias)
                .withConsistentRead(true);
        Item outcome = table.getItem(getItemSpec);
        return outcome != null;
    }

    public FollowingResponse getFollowees(FollowingRequest request) {
        User follower = request.getFollower();
        User lastFollowee = request.getLastFollowee();
        int limit = request.getLimit();

        List<User> followees = new ArrayList<>();

        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#followerAlias", followerAliasAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":alias", new AttributeValue().withS(follower.getAlias()));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(followeesTableName)
                .withKeyConditionExpression("#followerAlias = :alias")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(limit);

        if (lastFollowee != null) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(followerAliasAttr, new AttributeValue().withS(follower.getAlias()));
            startKey.put(followeeAliasAttr, new AttributeValue().withS(lastFollowee.getAlias()));
            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        QueryResult queryResult = amazonDynamoDB.query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();
        if (items != null) {
            for (Map<String, AttributeValue> item : items) {
                String followeeAlias = item.get(followeeAliasAttr).getS();
                String followeeFirstName = item.get(followeeFirstNameAttr).getS();
                String followeeLastName = item.get(followeeLastNameAttr).getS();
                String followeeImageUrl = item.get(followeeImageUrlAttr).getS();

                User followee = new User(followeeFirstName, followeeLastName, followeeAlias, followeeImageUrl);
                followees.add(followee);
            }
        }
        return new FollowingResponse(followees, (queryResult.getLastEvaluatedKey() != null));
    }






















}

















