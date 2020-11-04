package byu.edu.cs.tweeter.shared.domain;

import android.net.Uri;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Represents a user in the system.
 */
public class User implements Comparable<User> {

    private final String firstName;
    private final String lastName;
    private final String alias;
    private final String imageUrl;
    private final Uri imageUri;

    public User(@NotNull String firstName, @NotNull String lastName, String imageURL) {
        this(firstName, lastName, String.format("@%s%s", firstName, lastName), imageURL);
    }

    public User(@NotNull String firstName, @NotNull String lastName, @NotNull String alias, String imageURL) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
        this.imageUrl = imageURL;
        this.imageUri = null;
    }
    public User(@NotNull String firstName, @NotNull String lastName, Uri imageUri) {
        this(firstName, lastName, String.format("@%s%s", firstName, lastName), imageUri);
    }

    public User(@NotNull String firstName, @NotNull String lastName, @NotNull String alias, Uri imageUri) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
        this.imageUri = imageUri;
        this.imageUrl = null;

    }
    public String getName() {
        return firstName + " " + lastName;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAlias() {
        return alias;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(alias, user.alias) &&
                Objects.equals(imageUrl, user.imageUrl) &&
                Objects.equals(imageUri, user.imageUri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, alias, imageUrl, imageUri);
    }

    @Override
    public int compareTo(User user) {
        return this.getAlias().compareTo(user.getAlias());
    }
}
