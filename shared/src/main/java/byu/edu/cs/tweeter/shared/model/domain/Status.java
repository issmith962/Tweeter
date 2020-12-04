package byu.edu.cs.tweeter.shared.model.domain;

import java.io.Serializable;
import java.util.Objects;

public class Status implements Comparable<Status>, Serializable {
    private User user;
    private long timestamp;
    private String status_text;

    public Status(User user, long date_posted, String status_text) {
        this.user = user;
        this.timestamp = date_posted;
        this.status_text = status_text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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
                Objects.equals(timestamp, status.timestamp) &&
                Objects.equals(status_text, status.status_text);
    }

    public Status() {}
    @Override
    public int hashCode() {
        return Objects.hash(user, timestamp, status_text);
    }

    @Override
    public int compareTo(Status s) {
        if (this.equals(s)) {
            return 0;
        }
        if (timestamp < s.getTimestamp()) {
            return -1;
        }
        else if (timestamp > s.getTimestamp()) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
