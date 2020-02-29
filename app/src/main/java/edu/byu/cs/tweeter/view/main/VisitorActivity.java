package edu.byu.cs.tweeter.view.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.CheckUserFollowingRequest;
import edu.byu.cs.tweeter.net.response.CheckUserFollowingResponse;
import edu.byu.cs.tweeter.presenter.VisitorPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.CheckUserFollowingTask;
import edu.byu.cs.tweeter.view.asyncTasks.LoadImageTask;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.main.adapters.VisitingSectionsPagerAdapter;

public class VisitorActivity extends AppCompatActivity implements LoadImageTask.LoadImageObserver, VisitorPresenter.View, CheckUserFollowingTask.CheckUserFollowingObserver {
    private VisitorPresenter presenter;
    private User currentUser;
    private User visitingUser;
    private ImageView userImageView;
    private ViewPager viewPager;
    private boolean userIsFollowing;
    private boolean checkFollowingTaskCompleted;


    @Override
    protected void onCreate(Bundle savedInstance) {
        checkFollowingTaskCompleted = false;
        //TODO: Figure out how to use saved instance to get the name of the user to visit
        //FamilyMap probably has some stuff to help with this.
        super.onCreate(savedInstance);
        userIsFollowing = false;
        setContentView(R.layout.activity_visitor);
        Intent intent = getIntent();
        intent.getStringExtra("personId");
        String alias = intent.getStringExtra("alias");
        String firstName = intent.getStringExtra("firstName");
        String lastName = intent.getStringExtra("lastName");
        String imageURL = intent.getStringExtra("imageURL");
        if (firstName == null) {
            firstName = "";
        }
        if (lastName == null) {
            lastName = "";
        }
        if (alias == null) {
            alias = "";
        }
        if (imageURL == null) {
            imageURL = "";
        }
        visitingUser = new User(firstName, lastName, alias, imageURL);

        presenter = new VisitorPresenter(this);
        loadDisplay();
        // loadDisplay should contain the commands from loadUserDisplay and the necessary
        // commands from startFeedState to start the tabbed layout
    }

    public void loadDisplay() {
        updateCurrentUser();
        checkFollowingTaskCompleted = false;
        CheckUserFollowingTask task = new CheckUserFollowingTask(presenter, this);
        CheckUserFollowingRequest request = new CheckUserFollowingRequest(currentUser, visitingUser.getAlias());
        task.execute(request);

        userImageView = findViewById(R.id.userImage);
        TextView userName = findViewById(R.id.userName);
        TextView userAlias = findViewById(R.id.userAlias);

        LoadImageTask loadImageTask = new LoadImageTask(this);
        loadImageTask.execute(visitingUser.getImageUrl());
        userName.setText(visitingUser.getName());
        userAlias.setText(visitingUser.getAlias());

        VisitingSectionsPagerAdapter visitingSectionsPagerAdapter = new VisitingSectionsPagerAdapter(this, getSupportFragmentManager(), visitingUser);
        viewPager = findViewById(R.id.visitor_view_pager);
        viewPager.setAdapter(visitingSectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.visitor_tabs);
        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with search", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setVisibility(View.VISIBLE);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with logout", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Button followButton = findViewById(R.id.follow_button);
        // check if user is following or not...
        if (!checkFollowingTaskCompleted) {
            followButton.setText("...");
        }
        else if (userIsFollowing) {
            followButton.setText(R.string.follow);
        }
        else {
            followButton.setText(R.string.unfollow);
        }
        followButton.setVisibility(View.VISIBLE);
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with follow/unfollow", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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

        loadDisplay();
    }

    @Override
    public void userFollowingChecked(CheckUserFollowingResponse response) {
        checkFollowingTaskCompleted = true;
        userIsFollowing = response.isUserFollowing();
        Button followButton = findViewById(R.id.follow_button);
        if (userIsFollowing) {
            followButton.setText(R.string.follow);
        }
        else {
            followButton.setText(R.string.unfollow);
        }
    }

    private void updateCurrentUser() {
        currentUser = presenter.getCurrentUser();
    }
}




















