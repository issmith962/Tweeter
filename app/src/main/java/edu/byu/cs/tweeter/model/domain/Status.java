package edu.byu.cs.tweeter.model.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Status implements Comparable<Status> {
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

    @Override
    public int compareTo(Status s) {
        if (this.equals(s)) {
            return 0;
        }
        // "06/25/2009"
        // "01 2 34 5 678(10)"
        int month = Integer.parseInt(date_posted.substring(0,2));
        int otherMonth = Integer.parseInt(s.getDate_posted().substring(0,2));
        int day = Integer.parseInt(date_posted.substring(3,5));
        int otherDay = Integer.parseInt(s.getDate_posted().substring(3,5));
        int year = Integer.parseInt(date_posted.substring(6,10));
        int otherYear = Integer.parseInt(s.getDate_posted().substring(6,10));


        if (year < otherYear) {
            return -1;
        }
        else if (year > otherYear) {
            return 1;
        }
        else {
            if (month < otherMonth) {
                return -1;
            } else if (month > otherMonth) {
                return 1;
            } else {
                if (day < otherDay) {
                    return -1;
                } else if (day > otherDay) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }
}
