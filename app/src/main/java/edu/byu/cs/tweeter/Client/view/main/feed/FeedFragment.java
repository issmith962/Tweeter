package edu.byu.cs.tweeter.Client.view.main.feed;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import byu.edu.cs.tweeter.shared.model.domain.Status;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.request.FeedRequest;
import byu.edu.cs.tweeter.shared.response.FeedResponse;
import edu.byu.cs.tweeter.Client.presenter.FeedPresenter;
import edu.byu.cs.tweeter.Client.view.asyncTasks.GetFeedTask;
import edu.byu.cs.tweeter.Client.view.cache.ImageCache;
import edu.byu.cs.tweeter.Client.view.main.VisitorActivity;
import edu.byu.cs.tweeter.R;

public class FeedFragment extends Fragment implements FeedPresenter.View {
    private User user;
    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private FeedPresenter presenter;

    private FeedRecyclerViewAdapter feedRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        Bundle bundle = this.getArguments();
        String alias;
        String firstName;
        String lastName;
        String imageURL;
        Uri imageUri;
        if (bundle != null) {
            alias = bundle.getString("alias", "");
            firstName = bundle.getString("firstName", "");
            lastName = bundle.getString("lastName", "");
            imageURL = bundle.getString("imageURL", null);
            if (bundle.getString("imageUri") == null) {
                imageUri = null;
            }else {
                imageUri = Uri.parse(bundle.getString("imageUri"));
            }
        }
        else {
            alias = "";
            firstName = "";
            lastName = "";
            imageURL = null;
            imageUri = null;
        }
//        if (imageUri == null) {
        user = new User(firstName, lastName, alias, imageURL);
//        }
//        else {
//            user = new User(firstName, lastName, alias, imageUri);
//        }
        presenter = new FeedPresenter(this);

        RecyclerView feedRecyclerView = view.findViewById(R.id.feedRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        feedRecyclerView.setLayoutManager(layoutManager);

        feedRecyclerViewAdapter = new FeedRecyclerViewAdapter();
        feedRecyclerView.setAdapter(feedRecyclerViewAdapter);

        feedRecyclerView.addOnScrollListener(new FeedRecyclerViewPaginationScrollListener(layoutManager));

        return view;
    }

    private class StatusHolder extends RecyclerView.ViewHolder {
        private final ImageView statusImage;
        private final TextView statusAlias;
        private final TextView statusName;
        private final TextView statusDate;
        private final TextView statusText;

        StatusHolder(@NonNull View itemView) {
            super(itemView);

            statusImage = itemView.findViewById(R.id.status_image);
            statusAlias = itemView.findViewById(R.id.status_alias);
            statusName = itemView.findViewById(R.id.status_name);
            statusDate = itemView.findViewById(R.id.status_date);
            statusText = itemView.findViewById(R.id.status_text);
        }

        void bindStatus(Status status) {
            User user = status.getUser();
            statusImage.setImageDrawable(ImageCache.getInstance().getImageDrawable(user));
            statusAlias.setText(user.getAlias());
            statusName.setText(user.getName());

            String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date (status.getTimestamp()*1000));
            statusDate.setText(date.substring(0, 10));
            statusText.setText(status.getStatus_text());
            // Attempt at linkify
            Pattern userMatcher = Pattern.compile("@[(\\w)]*(\\b|$)");
            String visitViewURL = "visit://";
            Linkify.addLinks(statusText, Linkify.WEB_URLS);
            Linkify.addLinks(statusText, userMatcher, visitViewURL);
        }
    }

    private class FeedRecyclerViewAdapter extends RecyclerView.Adapter<StatusHolder> implements GetFeedTask.GetFeedObserver {

        private final List<Status> statuses = new ArrayList<>();

        private Status lastStatus;

        private boolean hasMorePages;
        private boolean isLoading = false;

        FeedRecyclerViewAdapter() {
            loadMoreItems();
        }

        void addItems(List<Status> newStatuses) {
            int startInsertPosition = statuses.size();
            statuses.addAll(newStatuses);
            this.notifyItemRangeInserted(startInsertPosition, newStatuses.size());
        }

        void addItem(Status status) {
            statuses.add(status);
            this.notifyItemInserted(statuses.size() - 1);
        }

        void removeItem(Status status) {
            int position = statuses.indexOf(status);
            statuses.remove(position);
            this.notifyItemRemoved(position);
        }

        @NonNull
        @Override
        public StatusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(FeedFragment.this.getContext());
            View view;

            if(viewType == LOADING_DATA_VIEW) {
                view =layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.status_row, parent, false);
            }

            return new StatusHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StatusHolder statusHolder, int position) {
            if(!isLoading) {
                statusHolder.bindStatus(statuses.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return statuses.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position == statuses.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();

            GetFeedTask getFeedTask = new GetFeedTask(presenter, this, getContext());
            FeedRequest request = new FeedRequest(user, PAGE_SIZE, lastStatus, presenter.findCurrentAuthToken());
            getFeedTask.execute(request);
        }

        @Override
        public void feedRetrieved(FeedResponse feedResponse) {
            List<Status> feed = feedResponse.getFeed();

            lastStatus = (feed.size() > 0) ? feed.get(feed.size() -1) : null;
            hasMorePages = feedResponse.hasMorePages();

            isLoading = false;
            removeLoadingFooter();

            feedRecyclerViewAdapter.addItems(feed);
        }

        private void addLoadingFooter() {
            addItem(new Status(new User("Dummy", "User", ""), 1607074976, "Sample Text..."));
        }

        private void removeLoadingFooter() {
            removeItem(statuses.get(statuses.size() - 1));
        }
    }

    private class FeedRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        FeedRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!feedRecyclerViewAdapter.isLoading && feedRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    feedRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }

    public void startVisitorActivity(User visitingUser) {
        Intent intent = new Intent(getActivity(), VisitorActivity.class);
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
}
