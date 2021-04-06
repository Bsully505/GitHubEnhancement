package github.tools.responseObjects;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GetPullRequestResponse extends ApiObjectResponse {
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
    private boolean isMerged;
    private String mergedByUser;
    private String mergedByUserUrl;
    private int numberOfComments;
    private int numberOfCommits;
    private int numberOfAdditions;
    private int numberOfDeletions;
    private int numberOfChangedFiles;

    public GetPullRequestResponse(JsonObject json) {
        super(json);
        mapParams(json);
    }

    public void mapParams(JsonObject json) {
        number = json.get("number").getAsInt();
        url = json.get("html_url").getAsString();
        state = json.get("state").getAsString();
        title = json.get("title").getAsString();
        JsonObject userResponse = json.get("user").getAsJsonObject();
        ownerName = userResponse.get("login").getAsString();
        ownerUrl = userResponse.get("html_url").getAsString();
        body = json.get("body").getAsString();
        labels = new ArrayList<Label>();
        JsonArray labelsResponse = json.get("labels").getAsJsonArray();
        for (int j = 0; j < labelsResponse.size(); j++) {
            String labelName = labelsResponse.get(j).getAsJsonObject().get("name").getAsString();
            String labelDescription = labelsResponse.get(j).getAsJsonObject().get("description").getAsString();
            Color labelColor = Color.decode("#" + labelsResponse.get(j).getAsJsonObject().get("description").getAsString());
            labels.add(new Label(labelName, labelDescription, labelColor));
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        createdAtTimestamp = LocalDateTime.parse(json.get("created_at").getAsString(), formatter);
        lastUpdatedTimestamp = LocalDateTime.parse(json.get("updated_at").getAsString(), formatter);
        closedAtTimestamp = null;
        if (json.has("closed_at") && !json.get("closed_at").isJsonNull()) {
            closedAtTimestamp = LocalDateTime.parse(json.get("closed_at").getAsString(), formatter);
        }
        mergedAtTimestamp = null;
        if (json.has("merged_at") && !json.get("merged_at").isJsonNull()) {
            mergedAtTimestamp = LocalDateTime.parse(json.get("merged_at").getAsString(), formatter);
        }
        isMerged = json.get("merged").getAsBoolean();
        mergedByUser = json.get("merged_by").getAsJsonObject().get("login").getAsString();
        mergedByUserUrl = json.get("merged_by").getAsJsonObject().get("html_url").getAsString();
        numberOfComments = json.get("comments").getAsInt();
        numberOfCommits = json.get("commits").getAsInt();
        numberOfAdditions = json.get("additions").getAsInt();
        numberOfDeletions = json.get("deletions").getAsInt();
        numberOfChangedFiles = json.get("changed_files").getAsInt();
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

    public boolean isMerged() {
        return isMerged;
    }

    public String getMergedByUser() {
        return mergedByUser;
    }

    public String getMergedByUserUrl() {
        return mergedByUserUrl;
    }

    public int getNumberOfComments() {
        return numberOfComments;
    }

    public int getNumberOfCommits() {
        return numberOfCommits;
    }

    public int getNumberOfAdditions() {
        return numberOfAdditions;
    }

    public int getNumberOfDeletions() {
        return numberOfDeletions;
    }

    public int getNumberOfChangedFiles() {
        return numberOfChangedFiles;
    }
}
