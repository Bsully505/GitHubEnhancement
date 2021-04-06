package github.tools.responseObjects;

import com.google.gson.JsonObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpdateUserResponse extends ApiObjectResponse {
    private String githubUsername;
    private String friendlyName;
    private String email;
    private String bio;
    private int numberOfPublicRepos;
    private int numberOfPrivateRepos;
    private int amountOfSpaceUsed;
    private int numberOfFollowers;
    private int numberFollowing;
    private LocalDateTime accountCreatedTimestamp;
    private String userUrl;


    public UpdateUserResponse(JsonObject json) {
        super(json);
        mapParams(json);
    }

    public void mapParams(JsonObject json) {
        githubUsername = json.get("login").getAsString();
        friendlyName = json.get("name").getAsString();
        if (json.has("email") && !json.get("email").isJsonNull()) {
            email = json.get("email").getAsString();
        } else {
            email = "";
        }
        if (json.has("bio") && !json.get("bio").isJsonNull()) {
            bio = json.get("bio").getAsString();
        } else {
            bio = "";
        }
        numberOfPublicRepos = json.get("public_repos").getAsInt();
        if (json.has("owned_private_repos") && !json.get("owned_private_repos").isJsonNull()) {
            numberOfPrivateRepos = json.get("owned_private_repos").getAsInt();
        } else {
            numberOfPrivateRepos = 0;
        }
        if (json.has("disk_usage") && !json.get("disk_usage").isJsonNull()) {
            amountOfSpaceUsed = json.get("disk_usage").getAsInt();
        } else {
            amountOfSpaceUsed = 0;
        }
        numberOfFollowers = json.get("followers").getAsInt();
        numberFollowing = json.get("following").getAsInt();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        accountCreatedTimestamp = LocalDateTime.parse(json.get("created_at").getAsString(), formatter);
        userUrl = json.get("html_url").getAsString();
    }

    public String getGithubUsername() {
        return githubUsername;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }

    public int getNumberOfPublicRepos() {
        return numberOfPublicRepos;
    }

    public int getNumberOfPrivateRepos() {
        return numberOfPrivateRepos;
    }

    public int getAmountOfSpaceUsed() {
        return amountOfSpaceUsed;
    }

    public int getNumberOfFollowers() {
        return numberOfFollowers;
    }

    public int getNumberFollowing() {
        return numberFollowing;
    }

    public LocalDateTime getAccountCreatedTimestamp() {
        return accountCreatedTimestamp;
    }

    public String getUserUrl() {
        return userUrl;
    }
}
