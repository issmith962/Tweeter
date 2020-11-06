package edu.byu.cs.tweeter.Client.view.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.Client.view.cache.ImageCache;
import edu.byu.cs.tweeter.R;
import byu.edu.cs.tweeter.shared.domain.User;
import byu.edu.cs.tweeter.shared.request.GetAllUsersRequest;
import byu.edu.cs.tweeter.shared.response.GetAllUsersResponse;
import edu.byu.cs.tweeter.Client.presenter.SearchPresenter;
import edu.byu.cs.tweeter.Client.view.asyncTasks.GetAllUsersTask;

public class SearchActivity extends AppCompatActivity implements SearchPresenter.View, GetAllUsersTask.GetAllUsersObserver {
    private User currentUser;
    private SearchPresenter presenter;
    private SearchRecyclerViewAdapter searchRecyclerViewAdapter;
    private SearchView searchView;
    private List<User> allUsers;
    private List<User> searchResults; // use aliases instead of users?
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        allUsers = new ArrayList<>();
        searchResults = new ArrayList<>();
        presenter = new SearchPresenter(this);
        updateCurrentUser();
        searchView = findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                // text has changed, apply filtering?
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                GetAllUsersTask getAllUsersTask = new GetAllUsersTask(presenter, SearchActivity.this, SearchActivity.this);
                getAllUsersTask.execute(new GetAllUsersRequest());
                // TODO: Check if this finishes in time to actually update the feed
                // Perform the final search
                query = s.toLowerCase();
                updateResults();
                updateUI();
                return false;
            }
        });

        RecyclerView searchRecyclerView = findViewById(R.id.search_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        searchRecyclerView.setLayoutManager(layoutManager);
        searchRecyclerViewAdapter = new SearchRecyclerViewAdapter(getApplicationContext(), searchResults);
        searchRecyclerView.setAdapter(searchRecyclerViewAdapter);
    }

    private void updateResults() {
        searchResults.clear();
        for (User user : allUsers) {
            if (user.getName().toLowerCase().contains(query) || user.getAlias().toLowerCase().contains(query)) {
                searchResults.add(user);
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
    private void updateUI() {
        if (searchRecyclerViewAdapter == null) {
            searchRecyclerViewAdapter = new SearchRecyclerViewAdapter(this, searchResults);
            RecyclerView searchRecyclerView = findViewById(R.id.search_recycler_view);

            searchRecyclerView.setAdapter(searchRecyclerViewAdapter);
        } else {
            searchRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void allUsersRetrieved(GetAllUsersResponse response) {
        allUsers = response.getAllUsers();
        updateResults();
        updateUI();
    }

    private class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchResultHolder> {
        private List<User> results;
        private Context context;

        public SearchRecyclerViewAdapter(Context context, List<User> results) {
            this.results = results;
            this.context = context;
        }


        @NonNull
        @Override
        public SearchResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            View view;
            view = layoutInflater.inflate(R.layout.user_row, parent, false);
            return new SearchResultHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchResultHolder holder, int position) {
            holder.bindUser(results.get(position));
        }

        @Override
        public int getItemCount() {
            return results.size();
        }
    }

    class SearchResultHolder extends RecyclerView.ViewHolder {
        private final ImageView userImage;
        private final TextView userAlias;
        private final TextView userName;
        private User holderUser;

        SearchResultHolder(@NonNull final View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.userImage);
            userAlias = itemView.findViewById(R.id.userAlias);
            userName = itemView.findViewById(R.id.userName);
            holderUser = null;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(view.getContext(), view);
                    popup.inflate(R.menu.popup_menu);
                    popup.show();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.story_menu_item:
                                    if (currentUser == null) {
                                        startVisitorActivity(holderUser);
                                    }
                                    else {
                                        if (holderUser.getAlias().equals(currentUser.getAlias())) {
                                            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                                            intent.putExtra("state", 1);
                                            startActivity(intent);
                                        } else {
                                            //Toast.makeText(getContext(), "You selected go to story", Toast.LENGTH_SHORT).show();
                                            startVisitorActivity(holderUser);
                                        }
                                    }
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                }
            });
        }

        void bindUser(User user) {
            userImage.setImageDrawable(ImageCache.getInstance().getImageDrawable(user));
            userAlias.setText(user.getAlias());
            userName.setText(user.getName());
            holderUser = user;
        }
    }

    public void startVisitorActivity(User visitingUser) {
        Intent intent = new Intent(SearchActivity.this, VisitorActivity.class);
        intent.putExtra("alias", visitingUser.getAlias());
        intent.putExtra("firstName", visitingUser.getFirstName());
        intent.putExtra("lastName", visitingUser.getLastName());
        intent.putExtra("imageURL", visitingUser.getImageUrl());
//        if (visitingUser.getImageUri() == null) {
//            intent.putExtra("imageUri", visitingUser.getImageUri());
//        }
//        else {
//            intent.putExtra("imageUri", visitingUser.getImageUri().toString());
//        }
        startActivity(intent);
    }

    private void updateCurrentUser() {
        currentUser = presenter.findCurrentUser();
    }

}