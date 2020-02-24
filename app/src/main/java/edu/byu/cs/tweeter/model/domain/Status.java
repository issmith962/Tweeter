package edu.byu.cs.tweeter.model.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class Status {
    private User user;
    private String date_posted;
    private String status_text;

    public Status(User user, String date_posted, String status_text) {
        this.user = user;
        this.date_posted = date_posted;
        this.status_text = status_text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDate_posted() {
        return date_posted;
    }

    public void setDate_posted(String date_posted) {
        this.date_posted = date_posted;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return Objects.equals(user, status.user) &&
                Objects.equals(date_posted, status.date_posted) &&
                Objects.equals(status_text, status.status_text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, date_posted, status_text);
    }
}
