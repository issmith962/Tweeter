package byu.edu.cs.tweeter.shared.response;

import java.util.Objects;

/**
 * A response that can indicate whether there is more data available from the server.
 */
public class PagedResponse extends Response {
    private boolean hasMorePages;

    PagedResponse(boolean success, boolean hasMorePages) {
        super(success);
        this.hasMorePages = hasMorePages;
    }

    PagedResponse(boolean success, String message, boolean hasMorePages) {
        super(success, message);
        this.hasMorePages = hasMorePages;
    }

    /**
     * An indicator of whether more data is available from the server. A value of true indicates
     * that the result was limited by a maximum value in the request and an additional request
     * would return additional data.
     *
     * @return true if more data is available; otherwise, false.
     */
    public boolean hasMorePages() {
        return hasMorePages;
    }

    public boolean isHasMorePages() {
        return hasMorePages;
    }

    public void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }

    public PagedResponse() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PagedResponse that = (PagedResponse) o;
        return hasMorePages == that.hasMorePages;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), hasMorePages);
    }
}
