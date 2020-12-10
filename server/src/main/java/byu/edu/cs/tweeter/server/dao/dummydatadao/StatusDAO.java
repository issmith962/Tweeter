package byu.edu.cs.tweeter.server.dao.dummydatadao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import byu.edu.cs.tweeter.shared.model.domain.Status;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.request.FeedRequest;
import byu.edu.cs.tweeter.shared.request.PostStatusRequest;
import byu.edu.cs.tweeter.shared.request.StoryRequest;
import byu.edu.cs.tweeter.shared.response.FeedResponse;
import byu.edu.cs.tweeter.shared.response.PostStatusResponse;
import byu.edu.cs.tweeter.shared.response.StoryResponse;

public class StatusDAO {
    private final User testUser = new User("Test", "User",
            "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    // Followees of Default Dummy User
    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
    private final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL);
    private final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
    private final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL);
    private final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL);
    private final User user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL);
    private final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL);
    private final User user9 = new User("Elliott", "Enderson", MALE_IMAGE_URL);
    private final User user10 = new User("Elizabeth", "Engle", FEMALE_IMAGE_URL);
    private final User user11 = new User("Frank", "Frandson", MALE_IMAGE_URL);
    private final User user12 = new User("Fran", "Franklin", FEMALE_IMAGE_URL);
    private final User user13 = new User("Gary", "Gilbert", MALE_IMAGE_URL);
    private final User user14 = new User("Giovanna", "Giles", FEMALE_IMAGE_URL);
    private final User user15 = new User("Henry", "Henderson", MALE_IMAGE_URL);
    private final User user16 = new User("Helen", "Hopwell", FEMALE_IMAGE_URL);
    private final User user17 = new User("Igor", "Isaacson", MALE_IMAGE_URL);
    private final User user18 = new User("Isabel", "Isaacson", FEMALE_IMAGE_URL);
    private final User user19 = new User("Justin", "Jones", MALE_IMAGE_URL);
    private final User user20 = new User("Jill", "Johnson", FEMALE_IMAGE_URL);

    private List<User> getDummyFollowees() {
        return Arrays.asList(user1, user2, user3, user4, user5, user6, user7,
                user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18,
                user19, user20);
    }

    // Statuses of Default Dummy User (story)
    private List<Status> generateTestUserStatuses() {
        List<Status> testUserStatuses;
        testUserStatuses = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            String day;
            if (i + 1< 10) {
                day = "0" + (i + 1);
            }
            else {
                day = Integer.toString(i + 1);
            }
            testUserStatuses.add(new Status(testUser, System.currentTimeMillis()/1000, "Status # " + i));
        }
        return testUserStatuses;
    }

    // Some random statuses of Dummy User Followees (feed)
    private List<Status> generateTestUserFolloweeStatuses() {
        List<Status> followeeStatuses;
        followeeStatuses = new ArrayList<>();
        int i = 0;
        for (User followee : getDummyFollowees()) {
            String day;
            if (i + 1 < 10) {
                day = "0" + (i + 1);
            } else {
                day = Integer.toString(i + 1);
            }
            followeeStatuses.add(new Status(followee, System.currentTimeMillis()/1000, "status #1 @AllenAnderson"));
            i++;
        }
        return followeeStatuses;
    }

    private String generateRandomDate() {
        // Random Date Generator
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        long minDay = LocalDate.of(1200, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2020, 10, 1).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        String date = dtf.format(randomDate).substring(0, 10);
        return date;
    }

//--------------------------------------------------------------------------------------------------
//                                         SERVICES
//--------------------------------------------------------------------------------------------------

    private int getStatusStartingIndex(Status lastStatus, List<Status> allStatuses) {
        int statusIndex = 0;

        if (lastStatus != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if (lastStatus.equals(allStatuses.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    statusIndex = i + 1;
                }
            }
        }

        return statusIndex;
    }

    public FeedResponse getFeed(FeedRequest request, List<User> allFollowees) {
        // TODO: Replace with proper search for all followeeStatuses in db
        assert(request.getLimit() > 0);
        assert(request.getUser() != null);

        // TODO: The following line must be replaced with a proper implementation
        //  param "allFollowees" will be used to find all the statuses
        List<Status> allFolloweeStatuses = generateTestUserFolloweeStatuses();

        Collections.sort(allFolloweeStatuses);
        Collections.reverse(allFolloweeStatuses);
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            int statusIndex = getStatusStartingIndex(request.getLastStatus(), allFolloweeStatuses);
            for (int limitCounter = 0; statusIndex < allFolloweeStatuses.size() && limitCounter < request.getLimit(); statusIndex++, limitCounter++) {
                responseStatuses.add(allFolloweeStatuses.get(statusIndex));
            }
            hasMorePages = statusIndex < allFolloweeStatuses.size();
        }
        System.out.println("hasMorePages: " + hasMorePages);
        return new FeedResponse(responseStatuses, hasMorePages);
    }

    public StoryResponse getStory(StoryRequest request) {
        assert(request.getLimit() > 0);
        assert(request.getUser() != null);

        // TODO: The following line must be replaced with a proper implementation
        //  of finding all statuses of given user in table
        List<Status> allUserStatuses = generateTestUserStatuses();

        List<Status> responseStatuses = new ArrayList<>(request.getLimit());
        boolean hasMorePages = false;

        if (request.getLimit() > 0) {
            if (allUserStatuses != null) {
                Collections.sort(allUserStatuses);
                Collections.reverse(allUserStatuses);
                int statusIndex = getStatusStartingIndex(request.getLastStatus(), allUserStatuses);
                for (int limitCounter = 0; statusIndex < allUserStatuses.size() && limitCounter < request.getLimit(); statusIndex++, limitCounter++) {
                    responseStatuses.add(allUserStatuses.get(statusIndex));
                }
                hasMorePages = statusIndex < allUserStatuses.size();
            }
        }
        return new StoryResponse(responseStatuses, hasMorePages);
    }

    public PostStatusResponse postStatus(PostStatusRequest request) {
        // TODO: Add new Status created here to database
        if (!request.getNewStatus().equals("")) {
            User user = request.getUser();
            Status newStatus = new Status(request.getUser(), request.getPostingTimestamp(), request.getNewStatus());
            //Collections.sort(postedStatuses);
            //Collections.reverse(postedStatuses);
            return new PostStatusResponse(true,"Status successfully posted!");
        } else {
            return new PostStatusResponse(false,"Status must have content...");
        }
    }



}
