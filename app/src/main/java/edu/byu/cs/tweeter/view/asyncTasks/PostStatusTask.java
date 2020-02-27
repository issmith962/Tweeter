package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import edu.byu.cs.tweeter.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.presenter.MainPresenter;

public class PostStatusTask extends AsyncTask<PostStatusRequest, Void, PostStatusResponse> {
    private final MainPresenter presenter;
    private final PostStatusAttemptObserver observer;

    public interface PostStatusAttemptObserver {
        void postStatusAttempted(PostStatusResponse postStatusResponse);
    }

    public PostStatusTask(MainPresenter presenter, PostStatusAttemptObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected PostStatusResponse doInBackground(PostStatusRequest... postStatusRequests) {
        PostStatusResponse response = presenter.postStatus(postStatusRequests[0]);
        return response;
    }

    @Override
    protected void onPostExecute(PostStatusResponse response) {
        if (observer != null) {
            observer.postStatusAttempted(response);
        }
    }
}
