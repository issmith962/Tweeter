package edu.byu.cs.tweeter.Client.view.asyncTasks;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.Client.presenter.StoryPresenter;
import edu.byu.cs.tweeter.Client.view.cache.ImageCache;
import edu.byu.cs.tweeter.Client.view.util.ImageUtils;
import byu.edu.cs.tweeter.shared.request.StoryRequest;
import byu.edu.cs.tweeter.shared.response.StoryResponse;

public class GetStoryTask extends AsyncTask<StoryRequest, Void, StoryResponse> {
    private final StoryPresenter presenter;
    private final GetStoryObserver observer;
    private Context context;

    public interface GetStoryObserver {
        void storyRetrieved(StoryResponse storyResponse);
    }

    public GetStoryTask(StoryPresenter presenter, GetStoryObserver observer, Context context) {
        this.presenter = presenter;
        this.observer = observer;
        this.context = context;
    }

    @Override
    protected StoryResponse doInBackground(StoryRequest... storyRequests) {
        StoryResponse response = presenter.getStory(storyRequests[0]);
        loadImages(response);
        return response;
    }

    private void loadImages(StoryResponse response) {
        for (byu.edu.cs.tweeter.shared.model.domain.Status status : response.getStory()) {
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
    protected void onPostExecute(StoryResponse storyResponse) {
        if (observer != null) {
            observer.storyRetrieved(storyResponse);
        }
    }

}
