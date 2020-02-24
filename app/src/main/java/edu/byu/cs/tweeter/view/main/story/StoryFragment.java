package edu.byu.cs.tweeter.view.main.story;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.FollowingRequest;
import edu.byu.cs.tweeter.net.request.StoryRequest;
import edu.byu.cs.tweeter.net.response.FollowingResponse;
import edu.byu.cs.tweeter.net.response.StoryResponse;
import edu.byu.cs.tweeter.presenter.StoryPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetFollowingTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetStoryTask;
import edu.byu.cs.tweeter.view.cache.ImageCache;

public class StoryFragment extends Fragment implements StoryPresenter.View {

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private StoryPresenter presenter;

    private StoryRecyclerViewAdapter storyRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story, container, false);

        presenter = new StoryPresenter(this);

        RecyclerView storyRecyclerView = view.findViewById(R.id.storyRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        storyRecyclerView.setLayoutManager(layoutManager);

        storyRecyclerViewAdapter = new StoryRecyclerViewAdapter();
        storyRecyclerView.setAdapter(storyRecyclerViewAdapter);

        storyRecyclerView.addOnScrollListener(new StoryRecyclerViewPaginationScrollListener(layoutManager));

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
            statusDate.setText(status.getDate_posted());
            statusText.setText(status.getStatus_text());
        }
    }

    private class StoryRecyclerViewAdapter extends RecyclerView.Adapter<StatusHolder> implements GetStoryTask.GetStoryObserver {

        private final List<Status> statuses = new ArrayList<>();

        private edu.byu.cs.tweeter.model.domain.Status lastStatus;

        private boolean hasMorePages;
        private boolean isLoading = false;

        StoryRecyclerViewAdapter() {
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
            LayoutInflater layoutInflater = LayoutInflater.from(StoryFragment.this.getContext());
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

            GetStoryTask getStoryTask = new GetStoryTask(presenter, this);
            StoryRequest request = new StoryRequest(presenter.getCurrentUser(), PAGE_SIZE, lastStatus);
            getStoryTask.execute(request);
        }

        @Override
        public void storyRetrieved(StoryResponse storyResponse) {
            List<Status> story = storyResponse.getStory();

            lastStatus = (story.size() > 0) ? story.get(story.size() -1) : null;
            hasMorePages = storyResponse.hasMorePages();

            isLoading = false;
            removeLoadingFooter();
            storyRecyclerViewAdapter.addItems(statuses);
        }

        private void addLoadingFooter() {
            addItem(new Status(new User("Dummy", "User", ""), "1/1/1111", "Sample Text..."));
        }

        private void removeLoadingFooter() {
            removeItem(statuses.get(statuses.size() - 1));
        }
    }

    private class StoryRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        StoryRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!storyRecyclerViewAdapter.isLoading && storyRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    storyRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }
}
