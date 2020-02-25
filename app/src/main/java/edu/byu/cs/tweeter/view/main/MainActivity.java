package edu.byu.cs.tweeter.view.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.response.PagedResponse;
import edu.byu.cs.tweeter.presenter.MainPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.LoadImageTask;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.main.adapters.FeedSectionsPagerAdapter;
import edu.byu.cs.tweeter.view.main.adapters.LoginSectionsPagerAdapter;

/**
 * The main activity for the application. Contains tabs for feed, story, following, and followers.
 */
public class MainActivity extends AppCompatActivity implements LoadImageTask.LoadImageObserver, MainPresenter.View {
    private static final String[] STATES = new String[]{"Login", "Feed"};
    private int state;
    private MainPresenter presenter;
    private User user;
    private ImageView userImageView;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);
        state = 0; // State begins as login by default
        updateUser();
        loadUserDisplay();
        loadCurrentState();
    }

    public void loadCurrentState() {
        if (STATES[state].equals("Login")) {
            startLoginState();
        } else if (STATES[state].equals("Feed")) {
            startFeedState();
        }
    }

    public void updateUser() {
        user = presenter.getCurrentUser();
    }

    public void loadUserDisplay() {
        updateUser();
        userImageView = findViewById(R.id.userImage);
        TextView userName = findViewById(R.id.userName);
        TextView userAlias = findViewById(R.id.userAlias);
        if (user == null) {
            userImageView.setImageResource(R.drawable.question);
            userName.setText("");
            userAlias.setText("");

        } else {
            // Asynchronously load the user's image if a user is logged in
            LoadImageTask loadImageTask = new LoadImageTask(this);
            loadImageTask.execute(presenter.getCurrentUser().getImageUrl());
            userName.setText(user.getName());
            userAlias.setText(user.getAlias());
        }
    }

    public void startLoginState() {
        state = 0;
        LoginSectionsPagerAdapter loginSectionsPagerAdapter = new LoginSectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(loginSectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);
        if (fab != null) {
            fab.hide();
        }
        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) { Snackbar.make(view, "Replace with search", Snackbar.LENGTH_LONG)
                                                        .setAction("Action", null).show(); }});
        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setVisibility(View.GONE);
    }

    public void startFeedState() {
        state = 1;

        FeedSectionsPagerAdapter feedSectionsPagerAdapter = new FeedSectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(feedSectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);
        if (fab != null) {
            fab.show();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Snackbar.make(view, "Replace with search", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show(); }});
        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setVisibility(View.VISIBLE);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Snackbar.make(view, "Replace with logout", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show(); }});
    }

    @Override
    public void onResume() {
        super.onResume();

        updateUser();
        loadUserDisplay();
        loadCurrentState();
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

}