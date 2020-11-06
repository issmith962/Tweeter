package edu.byu.cs.tweeter.Client.view.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.byu.cs.tweeter.R;
import byu.edu.cs.tweeter.shared.model.domain.User;

/**
 * Contains utility methods for working with images in an Android application.
 */
public class ImageUtils {

    /**
     * Reads image data from the specified urlString and creates an Android Drawable object.
     *
     * @param urlString the url where the image data resides.
     * @return the image.
     * @throws IOException if an I/O error occurs while attempting to read the image data.
     */
    public static Drawable drawableFromUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap == null ? null : new BitmapDrawable(Resources.getSystem(), bitmap);
            } else {
                throw new IOException("Unable to read from url. Response code: " + connection.getResponseCode());
            }
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
    }

    public static Drawable drawableFromUri(Uri imageUri, Context context) {
        Drawable drawable;
        try {
            InputStream is = context.getContentResolver().openInputStream(imageUri);
            drawable = Drawable.createFromStream(is, imageUri.toString());
        } catch (FileNotFoundException e) {
            drawable = context.getResources().getDrawable(R.drawable.question);
        }
        return drawable;
    }

    public static Drawable makeDrawable(User user, Context context) throws IOException {
//        if (user.getImageUri() != null) {
//            return drawableFromUri(user.getImageUri(), context);
//        }
//        else if (user.getImageUrl() != null) {
        if (user.getImageUrl() != null) {
            return drawableFromUrl(user.getImageUrl());
        }
        else {
            return context.getResources().getDrawable( R.drawable.question );
        }
    }
}
