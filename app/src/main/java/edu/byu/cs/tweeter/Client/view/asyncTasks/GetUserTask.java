package edu.byu.cs.tweeter.Client.view.asyncTasks;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.Client.presenter.VisitorPresenter;
import edu.byu.cs.tweeter.Client.view.cache.ImageCache;
import edu.byu.cs.tweeter.Client.view.util.ImageUtils;
import byu.edu.cs.tweeter.shared.request.GetUserRequest;
import byu.edu.cs.tweeter.shared.response.GetUserResponse;

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
