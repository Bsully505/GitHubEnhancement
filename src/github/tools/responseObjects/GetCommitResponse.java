package github.tools.responseObjects;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GetCommitResponse extends ApiObjectResponse {
    private String commitHash;
    private String commitUrl;
    private String commitAuthorName;
    private String commitAuthorEmail;
    private LocalDateTime commitDate;
    private String commitMessage;
    private int additions;
    private int deletions;
    private int totalChanges;
    private ArrayList<CommitRepoFile> filesChanged = new ArrayList<>();

    public GetCommitResponse(JsonObject json) {
        super(json);
        mapParams(json);
    }

    public void mapParams(JsonObject json) {
        commitHash = json.get("sha").getAsString();
        commitUrl = json.get("html_url").getAsString();
        commitAuthorName = json.get("commit").getAsJsonObject().get("author").getAsJsonObject().get("name").getAsString();
        commitAuthorEmail = json.get("commit").getAsJsonObject().get("author").getAsJsonObject().get("email").getAsString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        commitDate = LocalDateTime.parse(json.get("commit").getAsJsonObject().get("author").getAsJsonObject().get("date").getAsString(), formatter);
        commitMessage = json.get("commit").getAsJsonObject().get("message").getAsString();
        additions = json.get("stats").getAsJsonObject().get("additions").getAsInt();
        deletions = json.get("stats").getAsJsonObject().get("additions").getAsInt();
        totalChanges = json.get("stats").getAsJsonObject().get("total").getAsInt();

        JsonArray files = json.get("files").getAsJsonArray();
        for (int i = 0; i < files.size(); i++) {
            String fileName = files.get(i).getAsJsonObject().get("filename").getAsString();
            int additions = files.get(i).getAsJsonObject().get("additions").getAsInt();
            int deletions = files.get(i).getAsJsonObject().get("deletions").getAsInt();
            int totalChanges = files.get(i).getAsJsonObject().get("changes").getAsInt();
            String fileUrl = files.get(i).getAsJsonObject().get("raw_url").getAsString();
            filesChanged.add(new CommitRepoFile(fileName, additions, deletions, totalChanges, fileUrl));
        }
    }

    public String getCommitHash() {
        return commitHash;
    }

    public String getCommitUrl() {
        return commitUrl;
    }

    public String getCommitAuthorName() {
        return commitAuthorName;
    }

    public String getCommitAuthorEmail() {
        return commitAuthorEmail;
    }

    public LocalDateTime getCommitDate() {
        return commitDate;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public int getAdditions() {
        return additions;
    }

    public int getDeletions() {
        return deletions;
    }

    public int getTotalChanges() {
        return totalChanges;
    }

    public ArrayList<CommitRepoFile> getFilesChanged() {
        return filesChanged;
    }
}
