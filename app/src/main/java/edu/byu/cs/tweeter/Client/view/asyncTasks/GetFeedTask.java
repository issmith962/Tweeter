package edu.byu.cs.tweeter.Client.view.asyncTasks;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.request.FeedRequest;
import byu.edu.cs.tweeter.shared.response.FeedResponse;
import edu.byu.cs.tweeter.Client.presenter.FeedPresenter;
import edu.byu.cs.tweeter.Client.view.cache.ImageCache;
import edu.byu.cs.tweeter.Client.view.util.ImageUtils;

public class GetFeedTask extends AsyncTask<FeedRequest, Void, FeedResponse> {
    private final FeedPresenter presenter;
    private final GetFeedObserver observer;
    private Context context;

    public interface GetFeedObserver {
        void feedRetrieved(FeedResponse feedResponse);
    }
    public GetFeedTask(FeedPresenter presenter, GetFeedObserver observer, Context context) {
        this.presenter = presenter;
        this.observer = observer;
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected FeedResponse doInBackground(FeedRequest... feedRequests) {
        FeedResponse response = presenter.getFeed(feedRequests[0]);
        loadImages(response);
        return response;
    }

    private void loadImages(FeedResponse response) {
        for (byu.edu.cs.tweeter.shared.model.domain.Status status : response.getFeed()) {
            Drawable drawable;
            try {
                drawable = ImageUtils.makeDrawable(status.getUser(), context);

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
