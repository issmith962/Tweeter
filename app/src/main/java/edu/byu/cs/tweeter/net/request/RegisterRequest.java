package edu.byu.cs.tweeter.net.request;

import android.net.Uri;

public class RegisterRequest {
    private String name;
    private String alias;
    private String password;
    private Uri imageUri;

    public RegisterRequest(String name, String alias, String password, Uri imageUri) {
        this.name = name;
        this.alias = alias;
        this.password = password;
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public String getPassword() {
        return password;
    }

    public Uri getImageUri() {
        return imageUri;
    }
}

