package edu.byu.cs.tweeter.view.asyncTasks;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.net.request.FeedRequest;
import edu.byu.cs.tweeter.net.response.FeedResponse;
import edu.byu.cs.tweeter.presenter.FeedPresenter;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class GetFeedTask extends AsyncTask<FeedRequest, Void, FeedResponse> {
    private final FeedPresenter presenter;
    private final GetFeedObserver observer;

    public interface GetFeedObserver {
        void feedRetrieved(FeedResponse feedResponse);
    }
    public GetFeedTask(FeedPresenter presenter, GetFeedObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected FeedResponse doInBackground(FeedRequest... feedRequests) {
        FeedResponse response = presenter.getFeed(feedRequests[0]);
        loadImages(response);
        return response;
    }

    private void loadImages(FeedResponse response) {
        for (edu.byu.cs.tweeter.model.domain.Status status : response.getFeed()) {
            Drawable drawable;
            try {
                drawable = ImageUtils.drawableFromUrl(status.getUser().getImageUrl());

            } catch (IOException e) {
                Log.e(this.getClass().getName(), e.toString(), e);
                drawable = null;
            }
            ImageCache.getInstance().cacheImage(status.getUser(), drawable);
        }
    }

    @Override
    protected void onPostExecute(FeedResponse feedResponse) {
        if (observer != null) {
            observer.feedRetrieved(feedResponse);
        }
    }

}
