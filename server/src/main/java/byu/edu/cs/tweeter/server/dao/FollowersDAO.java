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
import byu.edu.cs.tweeter.shared.request.FollowersRequest;
import byu.edu.cs.tweeter.shared.response.FollowersResponse;

public class FollowersDAO {
    private Table table;

    public FollowersDAO() {
        getFollowersTable();
    }
    private static final String followersTableName = "TweeterFollowers";

    private static final String followeeAliasAttr = "followeeAlias";
    private static final String followerAliasAttr = "followerAlias";

    private static final String followerFirstNameAttr = "followerFirstName";
    private static final String followerLastNameAttr = "followerLastName";
    private static final String followerImageUrlAttr = "followerImageUrl";

    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    private void getFollowersTable() {
        table = dynamoDB.getTable(followersTableName);
    }

    //  ----------------------------- DAO ACCESS METHODS ---------------------------------------

    public void addFollow(User followee, User follower) throws DataAccessException {
        try {
            PutItemOutcome outcome = table.putItem(
                    new Item().withPrimaryKey(followeeAliasAttr, followee.getAlias(), followerAliasAttr, follower.getAlias())
                    .withString(followerFirstNameAttr, follower.getFirstName())
                    .withString(followerLastNameAttr, follower.getLastName())
                    .withString(followerImageUrlAttr, follower.getImageUrl()));
        } catch (Exception e) {
            throw new DataAccessException("Error when adding new follow relationship to followers table");
        }
    }

    public void removeFollow(User followee, User follower) throws DataAccessException {
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey(followeeAliasAttr, followee.getAlias(), followerAliasAttr, follower.getAlias()));
        try {
            table.deleteItem(deleteItemSpec);
        } catch (Exception e) {
            throw new DataAccessException("Error when removing follow relationship from followers table");
        }
    }

    public boolean checkFollow(String followeeAlias, String followerAlias) {
        GetItemSpec getItemSpec = new GetItemSpec()
                .withPrimaryKey(followeeAliasAttr, followeeAlias, followerAliasAttr, followerAlias)
                .withConsistentRead(true);
        Item outcome = table.getItem(getItemSpec);
        return outcome != null;
    }

    public FollowersResponse getFollowers(FollowersRequest request) {
        User followee = request.getFollowee();
        User lastFollower = request.getLastFollower();
        int limit = request.getLimit();

        List<User> followers = new ArrayList<>();

        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#followeeAlias", followeeAliasAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":alias", new AttributeValue().withS(followee.getAlias()));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(followersTableName)
                .withKeyConditionExpression("#followeeAlias = :alias")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(limit);

        if (lastFollower != null) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(followeeAliasAttr, new AttributeValue().withS(followee.getAlias()));
            startKey.put(followerAliasAttr, new AttributeValue().withS(lastFollower.getAlias()));
            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        QueryResult queryResult = amazonDynamoDB.query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();
        if (items != null) {
            for (Map<String, AttributeValue> item : items) {
                String followerAlias = item.get(followerAliasAttr).getS();
                String followerFirstName = item.get(followerFirstNameAttr).getS();
                String followerLastName = item.get(followerLastNameAttr).getS();
                String followerImageUrl = item.get(followerImageUrlAttr).getS();

                User follower = new User(followerFirstName, followerLastName, followerAlias, followerImageUrl);
                followers.add(follower);
            }
        }
        return new FollowersResponse(followers, (queryResult.getLastEvaluatedKey() != null));
    }

    public List<User> getAllFollowers(String followeeAlias) {
        List<User> allFollowers = new ArrayList<>();

        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#followeeAlias", followeeAliasAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":alias", new AttributeValue().withS(followeeAlias));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(followersTableName)
                .withKeyConditionExpression("#followeeAlias = :alias")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues);
        QueryResult queryResult = amazonDynamoDB.query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();
        if (items != null) {
            for (Map<String, AttributeValue> item : items) {
                String followerAlias = item.get(followerAliasAttr).getS();
                String followerFirstName = item.get(followerFirstNameAttr).getS();
                String followerLastName = item.get(followerLastNameAttr).getS();
                String followerImageUrl = item.get(followerImageUrlAttr).getS();

                User follower = new User(followerFirstName, followerLastName, followerAlias, followerImageUrl);
                allFollowers.add(follower);
            }
        }
        return allFollowers;
    }



}
