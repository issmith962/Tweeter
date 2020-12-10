package byu.edu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byu.edu.cs.tweeter.server.dao.DAOHelpers.DAOHelperFunctions;
import byu.edu.cs.tweeter.shared.model.domain.Status;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.net.DataAccessException;
import byu.edu.cs.tweeter.shared.response.StoryResponse;

public class StoryDAO {
    private Table table;

    public StoryDAO() {
        getStoryTable();
    }

    private static final String storyTableName = "TweeterStories";

    private static final String aliasAttr = "alias";
    private static final String datePlusPostedByAttr = "datePlusPostedBy";
    private static final String statusTextAttr = "statusText";

    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    private void getStoryTable() {
        table = dynamoDB.getTable(storyTableName);
    }

    //  ----------------------------- DAO ACCESS METHODS ---------------------------------------

    public void postStatusToStory(String alias, String datePlusPostedBy, String statusText) throws DataAccessException {
        try {
            PutItemOutcome outcome = table.putItem(
                    new Item().withPrimaryKey(aliasAttr, alias, datePlusPostedByAttr, datePlusPostedBy)
                    .withString(statusTextAttr, statusText));
        } catch (Exception e) {
            throw new DataAccessException("Error when adding new status to story");
        }
    }

    public StoryResponse getStory(User user, int limit, String last_datePlusPostedBy) throws DataAccessException {
        List<Status> story = new ArrayList<>();

        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#al", aliasAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":alias", new AttributeValue().withS(user.getAlias()));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(storyTableName)
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

                long timestamp = (DAOHelperFunctions.retrieveTimeStampFromString(timestampAsString));
                if (timestamp == -1) {
                    throw new DataAccessException("Invalid timestamp format");
                }
                Status status = new Status(user, timestamp, statusText);
                story.add(status);
            }
        }
        return new StoryResponse(story, (queryResult.getLastEvaluatedKey() != null));
    }
}
