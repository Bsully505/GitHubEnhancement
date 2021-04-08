package github.tools.responseObjects;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PullRequest {
    private int number;
    private String url;
    private String state;
    private String title;
    private String ownerName;
    private String ownerUrl;
    private String body;
    private ArrayList<Label> labels;
    private LocalDateTime createdAtTimestamp;
    private LocalDateTime lastUpdatedTimestamp;
    private LocalDateTime closedAtTimestamp;
    private LocalDateTime mergedAtTimestamp;

    public PullRequest(int number, String url, String state, String title, String ownerName, String ownerUrl, String body,
                       ArrayList<Label> labels, LocalDateTime createdAtTimestamp, LocalDateTime lastUpdatedTimestamp,
                       LocalDateTime closedAtTimestamp, LocalDateTime mergedAtTimestamp) {
        this.number = number;
        this.url = url;
        this.state = state;
        this.title = title;
        this.ownerName = ownerName;
        this.ownerUrl = ownerUrl;
        this.body = body;
        this.labels = labels;
        this.createdAtTimestamp = createdAtTimestamp;
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
        this.closedAtTimestamp = closedAtTimestamp;
        this.mergedAtTimestamp = mergedAtTimestamp;
    }

    public int getNumber() {
        return number;
    }

    public String getUrl() {
        return url;
    }

    public String getState() {
        return state;
    }

    public String getTitle() {
        return title;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerUrl() {
        return ownerUrl;
    }

    public String getBody() {
        return body;
    }

    public ArrayList<Label> getLabels() {
        return labels;
    }

    public LocalDateTime getCreatedAtTimestamp() {
        return createdAtTimestamp;
    }

    public LocalDateTime getLastUpdatedTimestamp() {
        return lastUpdatedTimestamp;
    }

    public LocalDateTime getClosedAtTimestamp() {
        return closedAtTimestamp;
    }

    public LocalDateTime getMergedAtTimestamp() {
        return mergedAtTimestamp;
    }
}
