package edu.byu.cs.tweeter.Client.view.asyncTasks;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.Client.presenter.SearchPresenter;
import edu.byu.cs.tweeter.Client.view.cache.ImageCache;
import edu.byu.cs.tweeter.Client.view.util.ImageUtils;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.request.GetAllUsersRequest;
import byu.edu.cs.tweeter.shared.response.GetAllUsersResponse;

public class GetAllUsersTask extends AsyncTask<GetAllUsersRequest, Void, GetAllUsersResponse> {
    private final SearchPresenter presenter;
    private final GetAllUsersObserver observer;
    private Context context;

    public interface GetAllUsersObserver {
        void allUsersRetrieved(GetAllUsersResponse response);
    }
    public GetAllUsersTask(SearchPresenter presenter, GetAllUsersObserver observer, Context context) {
        this.presenter = presenter;
        this.observer = observer;
        this.context = context;
    }
    @Override
    protected GetAllUsersResponse doInBackground(GetAllUsersRequest... getAllUsersRequests) {
        GetAllUsersResponse response;
        response = presenter.getAllUsers(getAllUsersRequests[0]);
        loadImages(response);
        return response;
    }

    private void loadImages(GetAllUsersResponse response) {
        if (response.getAllUsers() != null) {
            for (User user : response.getAllUsers()) {
                Drawable drawable;
                try {
                    drawable = ImageUtils.makeDrawable(user, context);

                } catch (IOException e) {
                    Log.e(this.getClass().getName(), e.toString(), e);
                    drawable = null;
                }
                ImageCache.getInstance().cacheImage(user, drawable);
            }
        }
    }

    @Override
    protected void onPostExecute(GetAllUsersResponse getAllUsersResponse) {
        if (observer != null) {
            observer.allUsersRetrieved(getAllUsersResponse);
        }
    }
}