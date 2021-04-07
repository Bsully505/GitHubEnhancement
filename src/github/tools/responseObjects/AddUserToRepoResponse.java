package github.tools.responseObjects;

import com.google.gson.JsonObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddUserToRepoResponse extends github.tools.responseObjects.ApiObjectResponse {
    private String repoName;
    private String repoFullName;
    private String repoOwner;
    private String repoOwnerUrl;
    private String inviteeUsername;
    private String inviteeUserUrl;
    private String inviterUserName;
    private String inviterUrl;
    private LocalDateTime invitedAtTimestamp;

    public AddUserToRepoResponse(JsonObject json) {
        super(json);
        mapParams(json);
    }

    public void mapParams(JsonObject json) {
        repoName = json.get("repository").getAsJsonObject().get("name").getAsString();
        repoFullName = json.get("repository").getAsJsonObject().get("full_name").getAsString();
        repoOwner = json.get("repository").getAsJsonObject().get("owner").getAsJsonObject().get("login").getAsString();
        repoOwnerUrl = json.get("repository").getAsJsonObject().get("owner").getAsJsonObject().get("html_url").getAsString();
        inviteeUsername = json.get("invitee").getAsJsonObject().get("login").getAsString();
        inviteeUserUrl = json.get("invitee").getAsJsonObject().get("html_url").getAsString();
        inviteeUsername = json.get("inviter").getAsJsonObject().get("login").getAsString();
        inviteeUserUrl = json.get("inviter").getAsJsonObject().get("html_url").getAsString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        invitedAtTimestamp = LocalDateTime.parse(json.get("created_at").getAsString(), formatter);

    }

    public String getRepoName() {
        return repoName;
    }

    public String getRepoFullName() {
        return repoFullName;
    }

    public String getRepoOwner() {
        return repoOwner;
    }

    public String getRepoOwnerUrl() {
        return repoOwnerUrl;
    }

    public String getInviteeUsername() {
        return inviteeUsername;
    }

    public String getInviteeUserUrl() {
        return inviteeUserUrl;
    }

    public String getInviterUserName() {
        return inviterUserName;
    }

    public String getInviterUrl() {
        return inviterUrl;
    }

    public LocalDateTime getInvitedAtTimestamp() {
        return invitedAtTimestamp;
    }
}
