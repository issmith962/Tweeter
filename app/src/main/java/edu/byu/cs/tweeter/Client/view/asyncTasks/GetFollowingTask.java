package edu.byu.cs.tweeter.Client.view.asyncTasks;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import byu.edu.cs.tweeter.shared.net.TweeterRemoteException;
import edu.byu.cs.tweeter.Client.presenter.FollowingPresenter;
import edu.byu.cs.tweeter.Client.view.cache.ImageCache;
import edu.byu.cs.tweeter.Client.view.util.ImageUtils;
import byu.edu.cs.tweeter.shared.model.domain.User;
import byu.edu.cs.tweeter.shared.request.FollowingRequest;
import byu.edu.cs.tweeter.shared.response.FollowingResponse;

/**
 * An {@link AsyncTask} for retrieving followees for a user.
 */
public class GetFollowingTask extends AsyncTask<FollowingRequest, Void, FollowingResponse> {

    private final FollowingPresenter presenter;
    private final GetFolloweesObserver observer;
    private Context context;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface GetFolloweesObserver {
        void followeesRetrieved(FollowingResponse followingResponse);
    }

    /**
     * Creates an instance.
     *  @param presenter the presenter from whom this task should retrieve followees.
     * @param observer the observer who wants to be notified when this task completes.
     * @param context
     */
    public GetFollowingTask(FollowingPresenter presenter, GetFolloweesObserver observer, Context context) {
        this.presenter = presenter;
        this.observer = observer;
        this.context = context;
    }

    /**
     * The method that is invoked on the background thread to retrieve followees.
     *
     * @param followingRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected FollowingResponse doInBackground(FollowingRequest... followingRequests) {
        FollowingResponse response = null;
        try {
            response = presenter.getFollowing(followingRequests[0]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
        loadImages(response);
        return response;
    }

    /**
     * Loads the image associated with each followee included in the response.
     *
     * @param response the response from the followee request.
     */
    private void loadImages(FollowingResponse response) {
        for(User user : response.getFollowees()) {

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

    /**
     * Notifies the observer (on the UI thread) when the task completes.
     *
     * @param followingResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(FollowingResponse followingResponse) {

        if(observer != null) {
            observer.followeesRetrieved(followingResponse);
        }
    }
}
