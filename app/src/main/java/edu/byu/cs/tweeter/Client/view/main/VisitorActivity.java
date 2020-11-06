package edu.byu.cs.tweeter.Client.view.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import byu.edu.cs.tweeter.shared.domain.AuthToken;
import edu.byu.cs.tweeter.Client.view.asyncTasks.CheckUserFollowingTask;
import edu.byu.cs.tweeter.Client.view.asyncTasks.FollowUserTask;
import edu.byu.cs.tweeter.Client.view.asyncTasks.GetFollowerCountTask;
import edu.byu.cs.tweeter.Client.view.asyncTasks.LoadUriImageTask;
import edu.byu.cs.tweeter.Client.view.cache.ImageCache;
import edu.byu.cs.tweeter.Client.view.main.adapters.VisitingSectionsPagerAdapter;
import edu.byu.cs.tweeter.R;
import byu.edu.cs.tweeter.shared.domain.User;
import edu.byu.cs.tweeter.Client.model.services.LoginServiceProxy;
import byu.edu.cs.tweeter.shared.request.CheckUserFollowingRequest;
import byu.edu.cs.tweeter.shared.request.FollowUserRequest;
import byu.edu.cs.tweeter.shared.request.FolloweeCountRequest;
import byu.edu.cs.tweeter.shared.request.FollowerCountRequest;
import byu.edu.cs.tweeter.shared.request.GetUserRequest;
import byu.edu.cs.tweeter.shared.request.LogoutRequest;
import byu.edu.cs.tweeter.shared.request.UnfollowUserRequest;
import byu.edu.cs.tweeter.shared.response.CheckUserFollowingResponse;
import byu.edu.cs.tweeter.shared.response.FollowUserResponse;
import byu.edu.cs.tweeter.shared.response.FolloweeCountResponse;
import byu.edu.cs.tweeter.shared.response.FollowerCountResponse;
import byu.edu.cs.tweeter.shared.response.GetUserResponse;
import byu.edu.cs.tweeter.shared.response.LogoutResponse;
import byu.edu.cs.tweeter.shared.response.UnfollowUserResponse;
import edu.byu.cs.tweeter.Client.presenter.VisitorPresenter;
import edu.byu.cs.tweeter.Client.view.asyncTasks.GetFolloweeCountTask;
import edu.byu.cs.tweeter.Client.view.asyncTasks.GetUserTask;
import edu.byu.cs.tweeter.Client.view.asyncTasks.LoadImageTask;
import edu.byu.cs.tweeter.Client.view.asyncTasks.LogoutTask;
import edu.byu.cs.tweeter.Client.view.asyncTasks.UnfollowUserTask;

public class VisitorActivity extends AppCompatActivity implements LoadImageTask.LoadImageObserver,
        LoadUriImageTask.LoadUriImageObserver, VisitorPresenter.View,
        CheckUserFollowingTask.CheckUserFollowingObserver, FollowUserTask.FollowUserObserver,
        UnfollowUserTask.UnfollowUserObserver, GetUserTask.GetUserObserver,
        GetFollowerCountTask.FollowerCountObserver, GetFolloweeCountTask.FolloweeCountObserver,
        LogoutTask.LogoutObserver {
    private VisitorPresenter presenter;
    private User currentUser;
    private User visitingUser;
    private ImageView userImageView;
    private TextView userName;
    private TextView userAlias;
    private ViewPager viewPager;
    private boolean userIsFollowing;
    private boolean checkFollowingTaskCompleted;
    private String username;
    private boolean flag;
    private TextView userFollowerCount;
    private TextView userFolloweeCount;


    @Override
    protected void onCreate(Bundle savedInstance) {
        checkFollowingTaskCompleted = false;
        super.onCreate(savedInstance);
        userIsFollowing = false;
        setContentView(R.layout.activity_visitor);
        presenter = new VisitorPresenter(this);
        flag = false;
        Uri data = getIntent().getData();

        if(data != null) {
            String uri = data.toString();
            username = uri.substring(uri.indexOf("@"));
            GetUserTask task = new GetUserTask(presenter, this,this);
            task.execute(new GetUserRequest(username));
        }
        else {
            Intent intent = getIntent();
            intent.getStringExtra("personId");
            String alias = intent.getStringExtra("alias");
            String firstName = intent.getStringExtra("firstName");
            String lastName = intent.getStringExtra("lastName");
            String imageURL = intent.getStringExtra("imageURL");
            Uri imageUri;
            if (intent.getStringExtra("imageUri") == null) {
                imageUri = null;
            } else {
                imageUri = Uri.parse(intent.getStringExtra("imageUri"));
            }
            if (firstName == null) {
                firstName = "";
            }
            if (lastName == null) {
                lastName = "";
            }
            if (alias == null) {
                alias = "";
            }
//            if (imageUri == null) {
            visitingUser = new User(firstName, lastName, alias, imageURL);
//            } else {
//                visitingUser = new User(firstName, lastName, alias, imageUri);
//            }
            loadDisplay();
        }
//        while (!flag) {
//            try { Thread.sleep(100); }
//            catch (InterruptedException e) { e.printStackTrace(); }
//        }
        // loadDisplay should contain the commands from loadUserDisplay and the necessary
        // commands from startFeedState to start the tabbed layout
    }

    public void loadDisplay() {
        updateCurrentUser();
        checkFollowingTaskCompleted = false;

        checkUserFollowingVisitingUser();

        userImageView = findViewById(R.id.userImage);
        userName = findViewById(R.id.userName);
        userAlias = findViewById(R.id.userAlias);
//        if (visitingUser.getImageUri() == null) {
        LoadImageTask loadImageTask = new LoadImageTask(this);
        loadImageTask.execute(visitingUser.getImageUrl());
//        }
//        else {
//            LoadUriImageTask loadUriImageTask = new LoadUriImageTask(this, VisitorActivity.this);
//            loadUriImageTask.execute(visitingUser.getImageUri());
//        }
        userName.setText(visitingUser.getName());
        userAlias.setText(visitingUser.getAlias());
        userFollowerCount = findViewById(R.id.followerCountVisitor);
        userFolloweeCount = findViewById(R.id.followeeCountVisitor);

        GetFollowerCountTask followerCountTask = new GetFollowerCountTask(presenter,this);
        FollowerCountRequest followerCountRequest = new FollowerCountRequest(visitingUser);
        followerCountTask.execute(followerCountRequest);

        GetFolloweeCountTask followeeCountTask = new GetFolloweeCountTask(presenter,this);
        FolloweeCountRequest followeeCountRequest = new FolloweeCountRequest(visitingUser);
        followeeCountTask.execute(followeeCountRequest);

        VisitingSectionsPagerAdapter visitingSectionsPagerAdapter = new VisitingSectionsPagerAdapter(this, getSupportFragmentManager(), visitingUser);
        viewPager = findViewById(R.id.visitor_view_pager);
        viewPager.setAdapter(visitingSectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.visitor_tabs);
        Button searchButton = findViewById(R.id.search_button);
        searchButton.setVisibility(View.VISIBLE);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SearchActivity.class);
            }
        });
        Button logoutButton = findViewById(R.id.logout_button);
        if (currentUser == null) {
            logoutButton.setText(R.string.login_title);
        }
        else {
            logoutButton.setText(R.string.logout_title);
        }
        logoutButton.setVisibility(View.VISIBLE);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });


        Button followButton = findViewById(R.id.follow_button);
        // check if user is following or not...
        if (!checkFollowingTaskCompleted) {
            followButton.setText("...");
        }
        else if (userIsFollowing) {
            followButton.setText(R.string.unfollow);
        }
        else {
            followButton.setText(R.string.follow);
        }
        checkUserFollowingVisitingUser();
        if (currentUser != null) {
            followButton.setVisibility(View.VISIBLE);
        }
        else {
            followButton.setVisibility(View.GONE);
        }

        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userIsFollowing) {
                    UnfollowUserTask unfollowUserTask = new UnfollowUserTask(presenter, VisitorActivity.this);
                    UnfollowUserRequest request = new UnfollowUserRequest(currentUser, visitingUser, getCurrentAuthToken());
                    unfollowUserTask.execute(request);
                }
                else {
                    FollowUserTask followUserTask = new FollowUserTask(presenter, VisitorActivity.this);
                    FollowUserRequest request = new FollowUserRequest(currentUser, visitingUser, getCurrentAuthToken());
                    followUserTask.execute(request);
                }
            }
        });
    }

    private void clearUser() {
        presenter.updateCurrentUser(null);
        presenter.updateCurrentAuthToken(null);

    }

    public void reset() {
        LogoutRequest request = new LogoutRequest(getCurrentAuthToken());
        LogoutTask task = new LogoutTask(presenter, this);
        task.execute(request);
        clearUser();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
    }

    private void checkUserFollowingVisitingUser() {
        CheckUserFollowingTask task = new CheckUserFollowingTask(presenter, this);
        CheckUserFollowingRequest request = new CheckUserFollowingRequest(currentUser, visitingUser.getAlias(), getCurrentAuthToken());
        task.execute(request);
    }
    @Override
    public void imageLoadProgressUpdated(Integer progress) {
        // we're just loading one image. no need to indicate progress.
    }

    @Override
    public void imagesLoaded(Drawable[] drawables) {
        ImageCache.getInstance().cacheImage(visitingUser, drawables[0]);

        if (drawables[0] != null) {
            userImageView.setImageDrawable(drawables[0]);
        }
    }

    public void startActivity(Class aClass) {
        Intent intent = new Intent(this, aClass);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (visitingUser != null) {
            loadDisplay();
        }

    }

    @Override
    public void userFollowingChecked(CheckUserFollowingResponse response) {
        checkFollowingTaskCompleted = true;
        userIsFollowing = response.isUserFollowing();
        Button followButton = findViewById(R.id.follow_button);
        if (userIsFollowing) {
            followButton.setText(R.string.unfollow);
        }
        else {
            followButton.setText(R.string.follow);
        }
    }

    private void updateCurrentUser() {
        currentUser = getCurrentUser();
    }

    @Override
    public void followUserAttempted(FollowUserResponse response) {
        Toast.makeText(getApplicationContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
        checkUserFollowingVisitingUser();
        Button followButton = findViewById(R.id.follow_button);
        if (!checkFollowingTaskCompleted) {
            followButton.setText("...");
        }
        else if (userIsFollowing) {
            followButton.setText(R.string.unfollow);
        }
        else {
            followButton.setText(R.string.follow);
        }
    }

    @Override
    public void unfollowUserAttempted(UnfollowUserResponse response) {
        Toast.makeText(getApplicationContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
        checkUserFollowingVisitingUser();
        Button followButton = findViewById(R.id.follow_button);
        if (!checkFollowingTaskCompleted) {
            followButton.setText("...");
        }
        else if (userIsFollowing) {
            followButton.setText(R.string.unfollow);
        }
        else {
            followButton.setText(R.string.follow);
        }
    }

    @Override
    public void userRetrieved(GetUserResponse response) {
        visitingUser = response.getUser();
        if (visitingUser == null) {
            onBackPressed();
        }
        loadDisplay();
    }

    @Override
    public void followerCountRequested(FollowerCountResponse response) {
        userFollowerCount.setText("Followers: " + Integer.toString(response.getFollowerCount()));
    }

    @Override
    public void followeeCountRequested(FolloweeCountResponse response) {
        userFolloweeCount.setText("Followees: " + Integer.toString(response.getFolloweeCount()));
    }

    @Override
    public void logoutAttempted(LogoutResponse response) {
        Toast.makeText(getApplicationContext(), response.getMessage(), Toast. LENGTH_SHORT).show();
    }

    public User getCurrentUser() {
        return presenter.findCurrentUser();
    }
    public AuthToken getCurrentAuthToken() {
        return presenter.findCurrentAuthToken();
    }

}




















