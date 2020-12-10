package byu.edu.cs.tweeter.server.dao.dummydatadao;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import byu.edu.cs.tweeter.server.dao.FollowDAO;
import byu.edu.cs.tweeter.server.dao.UserDAO;
import byu.edu.cs.tweeter.shared.model.domain.User;

public class Filler {
    BasicAWSCredentials creds = new BasicAWSCredentials("AKIAW7KAIOO64CD3VUNI", "Z9kADH75JtrFKUaaKeg2Giv+RYWhZMr0LwBrtcLd");

    private static final String userTableName = "TweeterUsers";
    private static final String aliasAttr = "alias";
    private static final String firstNameAttr = "firstName";
    private static final String lastNameAttr = "lastName";
    private static final String imageUrlAttr = "imageUrl";
    private static final String securePasswordAttr = "securePassword";
    private static final String saltAttr = "salt";
    private static final String followeeCountAttr = "followeeCount";
    private static final String followerCountAttr = "followerCount";

    private static final String followsTableName = "TweeterFollows";
    private static final String followeesIndex = "followeesIndex";
    private static final String followerAliasAttr = "followerAlias";
    private static final String followerFirstNameAttr = "followerFirstName";
    private static final String followerLastNameAttr = "followerLastName";
    private static final String followerImageUrlAttr = "followerImageUrl";
    private static final String followeeAliasAttr = "followeeAlias";
    private static final String followeeFirstNameAttr = "followeeFirstName";
    private static final String followeeLastNameAttr = "followeeLastName";
    private static final String followeeImageUrlAttr = "followeeImageUrl";

    private static AmazonDynamoDB amazonDynamoDB;
    private static DynamoDB dynamoDB;
    private static AmazonS3 s3;

    public Filler() {
        amazonDynamoDB = AmazonDynamoDBClientBuilder
                .standard()
                .withRegion("us-west-2")
                .withCredentials(new AWSStaticCredentialsProvider(creds))
                .build();
        dynamoDB = new DynamoDB(amazonDynamoDB);
        s3 = AmazonS3ClientBuilder
                .standard()
                .withRegion("us-west-2")
                .withCredentials(new AWSStaticCredentialsProvider(creds))
                .build();

    }

    private final static int NUM_USERS = 10000;
    private final static User testUser = new User("Test", "User", "TestUser", "https://cs340-tweeter-profile-pic-storage.s3.amazonaws.com/TestUserProfPic.png");

    public void fillDatabase() {
        UserDAO userDAO = new UserDAO();
        FollowDAO followDAO = new FollowDAO();

        List<String> followers = new ArrayList<>();
        List<User> users = new ArrayList<>();

        for (int i = 1; i <= NUM_USERS; i++) {
            String firstName = "Guy";
            String lastName = Integer.toString(i);
            String alias = "guy" + i;
            String imageUrl = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
            User user = new User(firstName, lastName, alias, imageUrl);
            users.add(user);
            followers.add(alias);
        }
        if (users.size() > 0) {
            this.addUserBatch(users);
        }
        if (followers.size() > 0) {
            this.addFollowerBatch(testUser, users);
        }
    }

    public void addFollowerBatch(User followee, List<User> followers) {
        // add test user to table
        Table userTable = dynamoDB.getTable(userTableName);
        PutItemOutcome addUserOutcome = userTable.putItem(
                new Item().withPrimaryKey(aliasAttr, testUser.getAlias())
                        .withString(firstNameAttr, testUser.getFirstName())
                        .withString(lastNameAttr, testUser.getLastName())
                        .withString(securePasswordAttr, "64967b252ed30f5bda877e4c26c43d805ecdf0484a53b5fb9eb59a5b61bee721")
                        .withString(saltAttr, "99h1K5Zk3k0rw1ACBWihQw==")
                        .withString(imageUrlAttr, testUser.getImageUrl())
                        .withInt(followerCountAttr, NUM_USERS)
                        .withInt(followeeCountAttr, 0));

        // add all followers to table
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
        if (batch.size() > 0) {
            TableWriteItems tableWriteItems = new TableWriteItems(followsTableName)
                    .withItemsToPut(batch);
            BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(tableWriteItems);
            do {
                Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();
                if (outcome.getUnprocessedItems().size() != 0) {
                    outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);
                }
            } while (outcome.getUnprocessedItems().size() > 0);
        }

    }

    public void addUserBatch(List<User> users) {
        List<Item> batch = new ArrayList<>();
        for (User user : users) {
            Item item = new Item().withPrimaryKey(aliasAttr, user.getAlias())
                    .withString(firstNameAttr, user.getFirstName())
                    .withString(lastNameAttr, user.getLastName())
                    .withString(imageUrlAttr, user.getImageUrl())
                    .withString(securePasswordAttr, "64967b252ed30f5bda877e4c26c43d805ecdf0484a53b5fb9eb59a5b61bee721")
                    .withString(saltAttr, "99h1K5Zk3k0rw1ACBWihQw==")
                    .withString(imageUrlAttr, testUser.getImageUrl())
                    .withInt(followerCountAttr, 0)
                    .withInt(followeeCountAttr, 1);


            batch.add(item);

            if (batch.size() == 25) {
                TableWriteItems tableWriteItems = new TableWriteItems(userTableName)
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
        if (batch.size() > 0) {
            TableWriteItems tableWriteItems = new TableWriteItems(userTableName)
                    .withItemsToPut(batch);
            BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(tableWriteItems);
            do {
                Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();
                if (outcome.getUnprocessedItems().size() != 0) {
                    outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);
                }
            } while (outcome.getUnprocessedItems().size() > 0);
        }
    }



}
