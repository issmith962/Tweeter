package byu.edu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.request.CheckUserFollowingRequest;
import byu.edu.cs.tweeter.shared.request.FollowUserRequest;
import byu.edu.cs.tweeter.shared.request.FolloweeCountRequest;
import byu.edu.cs.tweeter.shared.request.FollowerCountRequest;
import byu.edu.cs.tweeter.shared.request.FollowersRequest;
import byu.edu.cs.tweeter.shared.request.FollowingRequest;
import byu.edu.cs.tweeter.shared.request.UnfollowUserRequest;
import byu.edu.cs.tweeter.shared.response.CheckUserFollowingResponse;
import byu.edu.cs.tweeter.shared.response.FollowUserResponse;
import byu.edu.cs.tweeter.shared.response.FollowersResponse;
import byu.edu.cs.tweeter.shared.response.FollowingResponse;
import byu.edu.cs.tweeter.shared.response.UnfollowUserResponse;

public class FollowDAO {
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

    // Followers of Default Dummy User
    private final User user21 = new User("Yisroel", "Bailey",FEMALE_IMAGE_URL);
    private final User user22 = new User("Kaydan", "Frazier", MALE_IMAGE_URL);
    private final User user23 = new User("Tasnim", "Solomon", MALE_IMAGE_URL);
    private final User user24 = new User("Sachin", "Rich", MALE_IMAGE_URL);
    private final User user25 = new User("Nia", "Davenport",FEMALE_IMAGE_URL);
    private final User user26 = new User("Tobi", "Carlson", MALE_IMAGE_URL);
    private final User user27 = new User("Izzy", "Berg",FEMALE_IMAGE_URL);
    private final User user28 = new User("Rayan", "Mercado", MALE_IMAGE_URL);
    private final User user29 = new User("Glyn", "Lara",FEMALE_IMAGE_URL);
    private final User user30= new User("Rueben", "Kidd", MALE_IMAGE_URL);
    private final User user31 = new User("Giselle", "Harrison",FEMALE_IMAGE_URL);
    private final User user32 = new User("Felix", "Begum", MALE_IMAGE_URL);
    private final User user33 = new User("Avni", "Beattie",FEMALE_IMAGE_URL);
    private final User user34 = new User("Terrence", "Cash", MALE_IMAGE_URL);
    private final User user35 = new User("Finnian", "Dickinson", MALE_IMAGE_URL);
    private final User user36 = new User("Mamie", "Butler",FEMALE_IMAGE_URL);
    private final User user37 = new User("Kaylan", "Barton", MALE_IMAGE_URL);
    private final User user38 = new User("Roan", "Sutton",FEMALE_IMAGE_URL);
    private final User user39 = new User("Abdurrahman", "Simmons", MALE_IMAGE_URL);
    private final User user40 = new User("Imani", "Burton", MALE_IMAGE_URL);

//--------------------------------------------------------------------------------------------------
//                                      FOLLOWEE SERVICES
//--------------------------------------------------------------------------------------------------

    public Integer getFolloweeCount(FolloweeCountRequest request) {
        // TODO: uses the dummy data.  Replace with a real implementation.
        assert request.getUser() != null;
        return getDummyFollowees().size();
    }

    public FollowingResponse getFollowees(FollowingRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        assert request.getLimit() > 0;
        assert request.getFollower() != null;

        List<User> allFollowees = getDummyFollowees();
        List<User> responseFollowees = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allFollowees != null) {
                int followeesIndex = getFolloweesStartingIndex(request.getLastFollowee(), allFollowees);

                for(int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                    responseFollowees.add(allFollowees.get(followeesIndex));
                }

                hasMorePages = followeesIndex < allFollowees.size();
            }
        }

        return new FollowingResponse(responseFollowees, hasMorePages);
    }

    private int getFolloweesStartingIndex(User lastFollowee, List<User> allFollowees) {

        int followeesIndex = 0;

        if(lastFollowee != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowees.size(); i++) {
                if(lastFollowee.equals(allFollowees.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followeesIndex = i + 1;
                    break;
                }
            }
        }

        return followeesIndex;
    }

    List<User> getDummyFollowees() {
        return Arrays.asList(user1, user2, user3, user4, user5, user6, user7,
                user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18,
                user19, user20);
    }
//--------------------------------------------------------------------------------------------------
//                                      FOLLOWER SERVICES
//--------------------------------------------------------------------------------------------------

    public Integer getFollowerCount(FollowerCountRequest request) {
        // TODO: uses the dummy data.  Replace with a real implementation.
        assert request.getUser() != null;
        return getDummyFollowers().size();
    }

    public FollowersResponse getFollowers(FollowersRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        assert request.getLimit() > 0;
        assert request.getFollowee() != null;

        List<User> allFollowers = getDummyFollowers();
        List<User> responseFollowers = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allFollowers != null) {
                int followeesIndex = getFollowersStartingIndex(request.getLastFollower(), allFollowers);

                for(int limitCounter = 0; followeesIndex < allFollowers.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                    responseFollowers.add(allFollowers.get(followeesIndex));
                }

                hasMorePages = followeesIndex < allFollowers.size();
            }
        }

        return new FollowersResponse(responseFollowers, hasMorePages);
    }

    private int getFollowersStartingIndex(User lastFollower, List<User> allFollowers) {

        int followeesIndex = 0;

        if(lastFollower != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowers.size(); i++) {
                if(lastFollower.equals(allFollowers.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followeesIndex = i + 1;
                    break;
                }
            }
        }

        return followeesIndex;
    }

    List<User> getDummyFollowers() {
        return Arrays.asList(user21, user22, user23, user24, user25, user26, user27,
                user28, user29, user30, user31, user32, user33, user34, user35, user36, user37, user38,
                user39, user40);
    }

//--------------------------------------------------------------------------------------------------
//                                      FOLLOW ACTION/CHECK SERVICES
//--------------------------------------------------------------------------------------------------

    public FollowUserResponse followUser(FollowUserRequest request) {
        // TODO: create relationship between request.getFollower() and request.getFollowee() in table
        return new FollowUserResponse(true, "Success: " +
                request.getFollower().getAlias() + " successfully followed " +
                request.getFollowee().getAlias());
    }

    public UnfollowUserResponse unfollowUser(UnfollowUserRequest request) {
        // TODO: delete relationship between request.getFollower() and request.getFollowee() in table
        return new UnfollowUserResponse(true, "Success: " +
                request.getFollower().getAlias() + " successfully unfollowed " +
                request.getFollowee().getAlias());
    }

    public CheckUserFollowingResponse isUserFollowing(CheckUserFollowingRequest request) {
        // TODO: check relationship between request.getFollower() and request.getFollowee() in table
        return new CheckUserFollowingResponse(true);
    }


}
