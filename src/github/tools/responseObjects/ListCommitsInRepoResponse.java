package github.tools.responseObjects;

import com.google.gson.JsonArray;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ListCommitsInRepoResponse extends ApiArrayResponse {

    private ArrayList<Commit> commits = new ArrayList<>();

    public ListCommitsInRepoResponse(JsonArray json) {
        super(json);
        mapParams(json);
    }

    public void mapParams(JsonArray json) {
        for (int i = 0; i < json.size(); i++) {
            String commitHash = json.get(i).getAsJsonObject().get("sha").getAsString();
            String commitUrl = json.get(i).getAsJsonObject().get("html_url").getAsString();
            String commitAuthorName = json.get(i).getAsJsonObject().get("commit").getAsJsonObject().get("author").getAsJsonObject().get("name").getAsString();
            String commitAuthorEmail = json.get(i).getAsJsonObject().get("commit").getAsJsonObject().get("author").getAsJsonObject().get("email").getAsString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            LocalDateTime commitDate = LocalDateTime.parse(json.get(i).getAsJsonObject().get("commit").getAsJsonObject().get("author").getAsJsonObject().get("date").getAsString(), formatter);
            String commitMessage = json.get(i).getAsJsonObject().get("commit").getAsJsonObject().get("message").getAsString();
            commits.add(new Commit(commitHash, commitUrl, commitAuthorName, commitAuthorEmail, commitDate, commitMessage));
        }
    }

    public ArrayList<Commit> getCommits() {
        return commits;
    }
}