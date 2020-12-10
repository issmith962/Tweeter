package byu.edu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.DataAccessException;
import byu.edu.cs.tweeter.shared.request.FollowersRequest;
import byu.edu.cs.tweeter.shared.request.FollowingRequest;
import byu.edu.cs.tweeter.shared.response.FollowersResponse;
import byu.edu.cs.tweeter.shared.response.FollowingResponse;

public class FollowDAO {
    private Table table;
    private static final String followeesIndex = "followeesIndex";

    public FollowDAO() {
        getFollowsTable();
    }

    private static final String followsTableName = "TweeterFollows";

    private static final String followerAliasAttr = "followerAlias";
    private static final String followerFirstNameAttr = "followerFirstName";
    private static final String followerLastNameAttr = "followerLastName";
    private static final String followerImageUrlAttr = "followerImageUrl";

    private static final String followeeAliasAttr = "followeeAlias";
    private static final String followeeFirstNameAttr = "followeeFirstName";
    private static final String followeeLastNameAttr = "followeeLastName";
    private static final String followeeImageUrlAttr = "followeeImageUrl";

    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    private void getFollowsTable() {
        table = dynamoDB.getTable(followsTableName);
    }

    //  ----------------------------- DAO ACCESS METHODS ---------------------------------------

    public void addFollow(User followee, User follower) throws DataAccessException {
        try {
            PutItemOutcome outcome = table.putItem(
                    new Item().withPrimaryKey(followeeAliasAttr, followee.getAlias(), followerAliasAttr, follower.getAlias())
                            .withString(followerFirstNameAttr, follower.getFirstName())
                            .withString(followerLastNameAttr, follower.getLastName())
                            .withString(followerImageUrlAttr, follower.getImageUrl())
                            .withString(followeeFirstNameAttr, followee.getFirstName())
                            .withString(followeeLastNameAttr, followee.getLastName())
                            .withString(followeeImageUrlAttr, followee.getImageUrl()));
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
                .withTableName(followsTableName)
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
                .withTableName(followsTableName)
                .withKeyConditionExpression("#followerAlias = :alias")
                .withIndexName(followeesIndex)
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

    public void addFollowerBatch(User followee, List<User> followers) {
        List<Item> batch = new ArrayList<>();
        for (User user : followers) {
            Item item = new Item().withPrimaryKey(followeeAliasAttr, followee.getAlias(),
                    followerAliasAttr, user.getAlias())
                    .withString(followerFirstNameAttr, user.getFirstName())
                    .withString(followerLastNameAttr, user.getLastName())
                    .withString(followerImageUrlAttr, user.getImageUrl())
                    .withString(followeeFirstNameAttr, followee.getFirstName())
                    .withString(followeeLastNameAttr, followee.getLastName())
                    .withString(followeeImageUrlAttr, followee.getImageUrl());
            batch.add(item);

            if (batch.size() == 25) {
                TableWriteItems tableWriteItems = new TableWriteItems(followsTableName)
                        .withItemsToPut(batch);
                BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(tableWriteItems);
                do {
                    Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();
                    if (outcome.getUnprocessedItems().size() != 0) {
                        outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);
                    }
                } while (outcome.getUnprocessedItems().size() > 0);

                batch = new ArrayList<>();
            }
        }
    }








}
