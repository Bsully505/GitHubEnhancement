package github.tools.responseObjects;

import com.google.gson.JsonArray;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ListReposResponse extends ApiArrayResponse {

    private ArrayList<Repo> repos = new ArrayList<>();

    public ListReposResponse(JsonArray json) {
        super(json);
        mapParams(json);
    }

    public void mapParams(JsonArray json) {
        for (int i = 0; i < json.size(); i++) {
            String repoName = json.get(i).getAsJsonObject().get("name").getAsString();
            String repoFullName = json.get(i).getAsJsonObject().get("full_name").getAsString();
            String ownerName = json.get(i).getAsJsonObject().get("owner").getAsJsonObject().get("login").getAsString();
            String ownerUrl = json.get(i).getAsJsonObject().get("owner").getAsJsonObject().get("html_url").getAsString();
            boolean isPrivate = json.get(i).getAsJsonObject().get("private").getAsBoolean();
            String description;
            if (!json.get(i).getAsJsonObject().get("description").isJsonNull()) {
                description = json.get(i).getAsJsonObject().get("description").getAsString();
            } else {
                description = "";
            }
            String url = json.get(i).getAsJsonObject().get("html_url").getAsString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            LocalDateTime createdTimestamp = LocalDateTime.parse(json.get(i).getAsJsonObject().get("created_at").getAsString(), formatter);
            LocalDateTime lastUpdatedTimestamp =  LocalDateTime.parse(json.get(i).getAsJsonObject().get("updated_at").getAsString(), formatter);
            repos.add(new Repo(repoName, repoFullName, ownerName, ownerUrl, isPrivate, description, url, createdTimestamp, lastUpdatedTimestamp));
        }
    }

    public ArrayList<Repo> getRepos() {
        return repos;
    }
}