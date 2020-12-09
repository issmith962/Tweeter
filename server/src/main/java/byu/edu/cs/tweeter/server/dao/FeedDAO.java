package byu.edu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byu.edu.cs.tweeter.shared.model.domain.Status;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.DataAccessException;
import byu.edu.cs.tweeter.shared.response.FeedResponse;

public class FeedDAO {
    private Table table;

    public FeedDAO() { getFeedTable();}

    private static final String feedTableName = "TweeterFeeds";

    private static final String aliasAttr = "alias";
    private static final String datePlusPostedByAttr = "datePlusPostedBy";
    private static final String statusTextAttr = "statusText";
    private static final String postedByAliasAttr = "postedByAlias";
    private static final String postedByFirstNameAttr = "postedByFirstName";
    private static final String postedByLastNameAttr = "postedByLastName";
    private static final String postedByImageUrlAttr = "postedByImageUrl";

    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();

    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    private void getFeedTable() {
        table = dynamoDB.getTable(feedTableName);
    }

    //  ----------------------------- DAO ACCESS METHODS ---------------------------------------

    public FeedResponse getFeed(User user, int limit, String last_datePlusPostedBy) throws DataAccessException {
        List<Status> feed = new ArrayList<>();

        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#al", aliasAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":alias", new AttributeValue().withS(user.getAlias()));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(feedTableName)
                .withKeyConditionExpression("#al = :alias")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(limit)
                .withScanIndexForward(false);

        if (DAOHelperFunctions.isNonEmptyString(last_datePlusPostedBy)) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(aliasAttr, new AttributeValue().withS(user.getAlias()));
            startKey.put(datePlusPostedByAttr, new AttributeValue().withS(last_datePlusPostedBy));
            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        QueryResult queryResult = amazonDynamoDB.query(queryRequest);

        List<Map<String, AttributeValue>> items = queryResult.getItems();
        if (items != null) {
            for (Map<String, AttributeValue> item : items) {
                String datePlusPostedBy = item.get(datePlusPostedByAttr).getS();
                String timestampAsString = datePlusPostedBy.split("@")[0];
                String statusText = item.get(statusTextAttr).getS();
                String postedByAlias = item.get(postedByAliasAttr).getS();
                String postedByFirstName = item.get(postedByFirstNameAttr).getS();
                String postedByLastName = item.get(postedByLastNameAttr).getS();
                String postedByImageUrl = item.get(postedByImageUrlAttr).getS();

                User postedByUser = new User(postedByFirstName, postedByLastName, postedByAlias, postedByImageUrl);

                long timestamp = (DAOHelperFunctions.retrieveTimeStampFromString(timestampAsString));
                if (timestamp == -1) {
                    throw new DataAccessException("Invalid timestamp format");
                }
                Status status = new Status(postedByUser, timestamp, statusText);
                feed.add(status);
            }
        }
        return new FeedResponse(feed, (queryResult.getLastEvaluatedKey() != null));
    }

    public void updateFeed(List<User> followees, Status status, String datePlusPostedBy) {
        User postedByUser = status.getUser();

        List<Item> batch = new ArrayList<>();
        for (User followee : followees) {
            Item item = new Item().withPrimaryKey(aliasAttr, followee.getAlias(), datePlusPostedByAttr, datePlusPostedBy)
                    .withString(statusTextAttr, status.getStatus_text())
                    .withString(postedByAliasAttr, status.getUser().getAlias())
                    .withString(postedByFirstNameAttr, status.getUser().getFirstName())
                    .withString(postedByLastNameAttr, status.getUser().getLastName())
                    .withString(postedByImageUrlAttr, status.getUser().getImageUrl());
            batch.add(item);
        }
        batchWriteToFeedTable(batch);
    }

    private void batchWriteToFeedTable(List<Item> batch) {
        TableWriteItems feedTableWriteItems = new TableWriteItems(feedTableName)
                .withItemsToPut(batch);
        BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(feedTableWriteItems);

        do {
            Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();
            if (outcome.getUnprocessedItems().size() != 0) {
                outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);
            }
        } while (outcome.getUnprocessedItems().size() > 0);
    }

    /*
    public void addStatusToFeed(String alias, String datePlusPostedBy, String statusText, User postedByUser) throws DataAccessException {
        try {
            PutItemOutcome outcome = table.putItem(
                    new Item().withPrimaryKey(aliasAttr, alias, datePlusPostedByAttr, datePlusPostedBy)
                            .withString(statusTextAttr, statusText)
                            .withString(postedByAliasAttr, postedByUser.getAlias())
                            .withString(postedByFirstNameAttr, postedByUser.getFirstName())
                            .withString(postedByLastNameAttr, postedByUser.getLastName())
                            .withString(postedByImageUrlAttr, postedByUser.getImageUrl()));
        } catch (Exception e) {
            throw new DataAccessException("Error when adding new status to a feed");
        }
    }
    */
}
