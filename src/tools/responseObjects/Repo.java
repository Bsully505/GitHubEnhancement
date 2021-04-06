package github.tools.responseObjects;

import java.time.LocalDateTime;

public class Repo {
    private String repoName;
    private String repoFullName;
    private String ownerName;
    private String ownerUrl;
    private boolean isPrivate;
    private String description;
    private String url;
    private LocalDateTime createdTimestamp;
    private LocalDateTime lastUpdatedTimestamp;

    public Repo(String repoName, String repoFullName, String ownerName, String ownerUrl, boolean isPrivate,
                String description, String url, LocalDateTime createdTimestamp, LocalDateTime lastUpdatedTimestamp) {
        this.repoName = repoName;
        this.repoFullName = repoFullName;
        this.ownerName = ownerName;
        this.ownerUrl = ownerUrl;
        this.isPrivate = isPrivate;
        this.description = description;
        this.url = url;
        this.createdTimestamp = createdTimestamp;
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
    }

    public String getRepoName() {
        return repoName;
    }

    public String getRepoFullName() {
        return repoFullName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerUrl() {
        return ownerUrl;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public LocalDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public LocalDateTime getLastUpdatedTimestamp() {
        return lastUpdatedTimestamp;
    }
}
