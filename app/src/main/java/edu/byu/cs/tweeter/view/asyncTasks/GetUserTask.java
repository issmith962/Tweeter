package edu.byu.cs.tweeter.view.asyncTasks;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.net.request.GetUserRequest;
import edu.byu.cs.tweeter.net.response.GetAllUsersResponse;
import edu.byu.cs.tweeter.net.response.GetUserResponse;
import edu.byu.cs.tweeter.net.response.StoryResponse;
import edu.byu.cs.tweeter.presenter.VisitorPresenter;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class GetUserTask extends AsyncTask<GetUserRequest, Void, GetUserResponse> {
    private final VisitorPresenter presenter;
    private final GetUserObserver observer;
    private Context context;

    public interface GetUserObserver {
        void userRetrieved(GetUserResponse response);
    }

    public GetUserTask(VisitorPresenter presenter, GetUserObserver observer, Context context) {
        this.presenter = presenter;
        this.observer = observer;
        this.context = context;
    }

    @Override
    protected GetUserResponse doInBackground(GetUserRequest... getUserRequests) {
        GetUserResponse response = presenter.getUser(getUserRequests[0]);
        loadImages(response);
        return response;
    }

    private void loadImages(GetUserResponse response) {
        Drawable drawable;
        try {
            drawable = ImageUtils.makeDrawable(response.getUser(), context);
        } catch (IOException e) {
            Log.e(this.getClass().getName(), e.toString(), e);
            drawable = null;
        }
        ImageCache.getInstance().cacheImage(response.getUser(), drawable);
    }

    @Override
    protected void onPostExecute(GetUserResponse response) {
        if (observer != null) {
            observer.userRetrieved(response);
        }
    }
}
