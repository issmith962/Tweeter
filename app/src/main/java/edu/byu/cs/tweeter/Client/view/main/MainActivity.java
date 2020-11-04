package edu.byu.cs.tweeter.Client.view.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.byu.cs.tweeter.Client.view.asyncTasks.GetFollowerCountTask;
import edu.byu.cs.tweeter.Client.view.asyncTasks.LoadUriImageTask;
import edu.byu.cs.tweeter.Client.view.asyncTasks.LogoutTask;
import edu.byu.cs.tweeter.Client.view.asyncTasks.PostStatusTask;
import edu.byu.cs.tweeter.Client.view.cache.ImageCache;
import edu.byu.cs.tweeter.Client.view.main.adapters.FeedSectionsPagerAdapter;
import edu.byu.cs.tweeter.Client.view.main.adapters.LoginSectionsPagerAdapter;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.Shared.domain.AuthToken;
import edu.byu.cs.tweeter.Shared.domain.User;
import edu.byu.cs.tweeter.Client.model.services.LoginService;
import edu.byu.cs.tweeter.Shared.request.FolloweeCountRequest;
import edu.byu.cs.tweeter.Shared.request.FollowerCountRequest;
import edu.byu.cs.tweeter.Shared.request.LogoutRequest;
import edu.byu.cs.tweeter.Shared.request.PostStatusRequest;
import edu.byu.cs.tweeter.Shared.response.FolloweeCountResponse;
import edu.byu.cs.tweeter.Shared.response.FollowerCountResponse;
import edu.byu.cs.tweeter.Shared.response.LogoutResponse;
import edu.byu.cs.tweeter.Shared.response.PostStatusResponse;
import edu.byu.cs.tweeter.Client.presenter.MainPresenter;
import edu.byu.cs.tweeter.Client.view.asyncTasks.GetFolloweeCountTask;
import edu.byu.cs.tweeter.Client.view.asyncTasks.LoadImageTask;

/**
 * The main activity for the application. Contains tabs for feed, story, following, and followers.
 */
public class MainActivity extends AppCompatActivity implements LogoutTask.LogoutObserver, GetFollowerCountTask.FollowerCountObserver, GetFolloweeCountTask.FolloweeCountObserver, LoadImageTask.LoadImageObserver, LoadUriImageTask.LoadUriImageObserver, MainPresenter.View, PostStatusTask.PostStatusAttemptObserver {
    private static final String[] STATES = new String[]{"Login", "Feed", "Reset", "Register"};
    private int state;
    private MainPresenter presenter;
    private User user;
    private ImageView userImageView;
    private TextView userName;
    private TextView userAlias;
    private TextView userFollowerCount;
    private TextView userFolloweeCount;
    private AuthToken authToken;

    private ViewPager viewPager;
    public static Context contextOfApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        state = intent.getIntExtra("state", 0);
        if (state == 2) {
            reset();
        }
        presenter = new MainPresenter(this);
        contextOfApplication = getApplicationContext();
        updateUser();
        loadUserDisplay();
        loadCurrentState();
    }

    public void loadCurrentState() {
        if (STATES[state].equals("Login")) {
            startLoginState();
        } else if (STATES[state].equals("Feed")) {
            startFeedState();
        } else if (STATES[state].equals("Reset")) {
            reset();
        }
    }
    private void clearUser() {
        LoginService.getInstance().setCurrentUser(null);
        LoginService.getInstance().setCurrentAuthToken(null);

    }
    public void updateUser() {
        user = getCurrentUser();
        authToken = getCurrentAuthToken();
    }

    public User getCurrentUser() {
        return presenter.getCurrentUser();
    }
    public AuthToken getCurrentAuthToken() {
        return presenter.getCurrentAuthToken();
    }

    public void loadUserDisplay() {
        updateUser();
        userImageView = findViewById(R.id.userImage);
        userName = findViewById(R.id.userName);
        userAlias = findViewById(R.id.userAlias);
        userFollowerCount = findViewById(R.id.followerCountMain);
        userFolloweeCount = findViewById(R.id.followeeCountMain);
        if (user == null) {
            userImageView.setImageResource(R.drawable.question);
            userName.setText("");
            userAlias.setText("");
            userFollowerCount.setText("");
            userFolloweeCount.setText("");
        } else {
            // Asynchronously load the user's image if a user is logged in

            if (presenter.getCurrentUser().getImageUri() == null) {
                LoadImageTask loadImageTask = new LoadImageTask(this);
                loadImageTask.execute(presenter.getCurrentUser().getImageUrl());
            }
            else {
                LoadUriImageTask loadUriImageTask = new LoadUriImageTask(this, MainActivity.this);
                loadUriImageTask.execute(presenter.getCurrentUser().getImageUri());
            }
            userName.setText(user.getName());
            userAlias.setText(user.getAlias());
            GetFollowerCountTask followerCountTask = new GetFollowerCountTask(presenter,this);
            FollowerCountRequest followerCountRequest = new FollowerCountRequest(user);
            followerCountTask.execute(followerCountRequest);

            GetFolloweeCountTask followeeCountTask = new GetFolloweeCountTask(presenter,this);
            FolloweeCountRequest followeeCountRequest = new FolloweeCountRequest(user);
            followeeCountTask.execute(followeeCountRequest);
        }
    }
// OLD RESET
//    public void reset() {
//        clearUser();
//        presenter = new MainPresenter(this);
//        loadUserDisplay();
//        state = 0;
//        loadCurrentState();
//    }

    public void reset() {
        LogoutRequest request = new LogoutRequest(authToken);
        LogoutTask task = new LogoutTask(presenter, this);
        task.execute(request);
        clearUser();
        Intent intent = new Intent(getContextOfApplication(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContextOfApplication().startActivity(intent);
    }

    public void startLoginState() {
        state = 0;
        LoginSectionsPagerAdapter loginSectionsPagerAdapter = new LoginSectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.main_view_pager);
        viewPager.setAdapter(loginSectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.main_tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);
        if (fab != null) {
            fab.hide();
        }
        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SearchActivity.class);
            }
        });
        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setVisibility(View.GONE);
    }

    public void startFeedState() {
        state = 1;

        FeedSectionsPagerAdapter feedSectionsPagerAdapter = new FeedSectionsPagerAdapter(this, getSupportFragmentManager(), user);
        viewPager = findViewById(R.id.main_view_pager);
        viewPager.setAdapter(feedSectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.main_tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);
        if (fab != null) {
            fab.show();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                popUpEditText();
            }
        });
        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SearchActivity.class);
            }
        });
        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setVisibility(View.VISIBLE);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
    }
    private void popUpEditText() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Status");

        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newStatus = input.getText().toString();
                // send async to add newStatus to statuses in database
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String date = dtf.format(now).substring(0, 10);
                User currentUser = presenter.getCurrentUser();
                PostStatusTask postStatusTask = new PostStatusTask(presenter, MainActivity.this);
                PostStatusRequest request = new PostStatusRequest(currentUser, newStatus, date, authToken);
                postStatusTask.execute(request);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    public void postStatusAttempted(PostStatusResponse response) {
        Toast.makeText(getApplicationContext(),response.getMessage(), Toast. LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (state != 3) {
            updateUser();
            loadUserDisplay();
            loadCurrentState();
        }
    }

    @Override
    public void imageLoadProgressUpdated(Integer progress) {
        // We're just loading one image. No need to indicate progress.
    }

    /**
     * A callback that indicates that the image for the user being displayed on this activity has
     * been loaded.
     *
     * @param drawables the drawables (there will only be one).
     */
    @Override
    public void imagesLoaded(Drawable[] drawables) {
        ImageCache.getInstance().cacheImage(user, drawables[0]);

        if (drawables[0] != null) {
            userImageView.setImageDrawable(drawables[0]);
        }
    }

    public void startActivity(Class aClass) {
        Intent intent = new Intent(this, aClass);
        startActivity(intent);
    }
    public static Context getContextOfApplication() {
        return contextOfApplication;
    }
    public void setState(int state) {
        this.state = state;
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
}