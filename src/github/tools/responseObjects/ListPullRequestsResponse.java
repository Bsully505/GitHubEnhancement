package github.tools.responseObjects;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ListPullRequestsResponse extends ApiArrayResponse {

    private ArrayList<PullRequest> pullRequests = new ArrayList<>();

    public ListPullRequestsResponse(JsonArray json) {
        super(json);
        mapParams(json);
    }

    public ArrayList<PullRequest> getPullRequests() {
        return pullRequests;
    }

    public void mapParams(JsonArray json) {
        for (int i = 0; i < json.size(); i++) {
            JsonObject pullRequestResponse = json.get(i).getAsJsonObject();
            int number = pullRequestResponse.get("number").getAsInt();
            String url = pullRequestResponse.get("html_url").getAsString();
            String state = pullRequestResponse.get("state").getAsString();
            String title = pullRequestResponse.get("title").getAsString();
            JsonObject userResponse = pullRequestResponse.get("user").getAsJsonObject();
            String ownerName = userResponse.get("login").getAsString();
            String ownerUrl = userResponse.get("html_url").getAsString();
            String body = pullRequestResponse.get("body").getAsString();
            ArrayList<github.tools.responseObjects.Label> labels = new ArrayList<github.tools.responseObjects.Label>();
            JsonArray labelsResponse = pullRequestResponse.get("labels").getAsJsonArray();
            for (int j = 0; j < labelsResponse.size(); j++) {
                String labelName = labelsResponse.get(j).getAsJsonObject().get("name").getAsString();
                String labelDescription = labelsResponse.get(j).getAsJsonObject().get("description").getAsString();
                Color labelColor = Color.decode("#" + labelsResponse.get(j).getAsJsonObject().get("description").getAsString());
                labels.add(new Label(labelName, labelDescription, labelColor));
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            LocalDateTime createdAtTimestamp = LocalDateTime.parse(pullRequestResponse.get("created_at").getAsString(), formatter);
            LocalDateTime lastUpdatedTimestamp = LocalDateTime.parse(pullRequestResponse.get("updated_at").getAsString(), formatter);
            LocalDateTime closedAtTimestamp = null;
            if (pullRequestResponse.has("closed_at") && !pullRequestResponse.get("closed_at").isJsonNull()) {
                closedAtTimestamp = LocalDateTime.parse(pullRequestResponse.get("closed_at").getAsString(), formatter);
            }
            LocalDateTime mergedAtTimestamp = null;
            if (pullRequestResponse.has("merged_at") && !pullRequestResponse.get("merged_at").isJsonNull()) {
                mergedAtTimestamp = LocalDateTime.parse(pullRequestResponse.get("merged_at").getAsString(), formatter);
            }
            pullRequests.add(new PullRequest(number, url, state, title, ownerName, ownerUrl, body, labels, createdAtTimestamp, lastUpdatedTimestamp, closedAtTimestamp, mergedAtTimestamp));
        }


    }
}

