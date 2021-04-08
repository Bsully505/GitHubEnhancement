package github.tools.responseObjects;

import com.google.gson.JsonObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateRepoResponse extends ApiObjectResponse {
    private String repoName;
    private String repoFullName;
    private String ownerName;
    private String ownerUrl;
    private boolean isPrivate;
    private String description;
    private String url;
    private LocalDateTime createdTimestamp;
    private LocalDateTime lastUpdatedTimestamp;

    public CreateRepoResponse(JsonObject json) {
        super(json);
        mapParams(json);
    }

    public void mapParams(JsonObject json) {
        repoName = json.get("name").getAsString();
        repoFullName = json.get("full_name").getAsString();
        ownerName = json.get("owner").getAsJsonObject().get("login").getAsString();
        ownerUrl = json.get("owner").getAsJsonObject().get("html_url").getAsString();
        isPrivate = json.get("private").getAsBoolean();
        if (!json.get("description").isJsonNull()) {
            description = json.get("description").getAsString();
        } else {
            description = "";
        }
        url = json.get("html_url").getAsString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        createdTimestamp = LocalDateTime.parse(json.get("created_at").getAsString(), formatter);
        lastUpdatedTimestamp = LocalDateTime.parse(json.get("updated_at").getAsString(), formatter);
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
