package edu.byu.cs.tweeter.view.asyncTasks;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.view.util.ImageUtils;

public class LoadUriImageTask extends AsyncTask<Uri, Integer, Drawable[]> {
    private final edu.byu.cs.tweeter.view.asyncTasks.LoadUriImageTask.LoadUriImageObserver observer;
    private Context context;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface LoadUriImageObserver {

        void imageLoadProgressUpdated(Integer progress);
        void imagesLoaded(Drawable [] drawables);
    }

    /**
     * Creates an instance.
     *
     * @param observer the observer who wants to be notified when this task completes.
     */
    public LoadUriImageTask(edu.byu.cs.tweeter.view.asyncTasks.LoadUriImageTask.LoadUriImageObserver observer, Context context) {
        this.observer = observer;
        this.context = context;
    }

    /**
     * The method that is invoked on the background thread to retrieve images.
     *
     * @param uris the urls from which images should be retrieved.
     * @return the images.
     */
    @Override
    protected Drawable [] doInBackground(Uri... uris) {

        Drawable [] drawables = new Drawable [uris.length];

        for(int i = 0; i < uris.length; i++) {

            drawables[i] = ImageUtils.drawableFromUri(uris[0], context);

            publishProgress((i / uris.length) * 100);
        }

        return drawables;
    }

    /**
     * Notifies the observer (on the UI thread) after each image is retrieved.
     *
     * @param values the progress indicator, represented as a percentage (between 0 and 100) of the
     *               total number of images to be loaded.
     */
    @Override
    protected void onProgressUpdate(Integer... values) {

        if(observer != null) {
            observer.imageLoadProgressUpdated(values[0]);
        }
    }

    /**
     * Notifies the observer (on the UI thread) when the task completes.
     *
     * @param drawables the images that were retrieved by the task.
     */
    @Override
    protected void onPostExecute(Drawable [] drawables) {
        if(observer != null) {
            observer.imagesLoaded(drawables);
        }
    }
}
