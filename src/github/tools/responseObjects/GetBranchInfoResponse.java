package github.tools.responseObjects;

import com.google.gson.JsonObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetBranchInfoResponse extends ApiObjectResponse {
    private String name;
    private String latestCommitHash;
    private String latestCommitAuthorName;
    private String latestCommitAuthorEmail;
    private String latestCommitMessage;
    private LocalDateTime latestCommitTimestamp;
    private boolean isProtected;


    public GetBranchInfoResponse(JsonObject json) {
        super(json);
        mapParams(json);
    }

    public void mapParams(JsonObject json) {
        name = json.get("name").getAsString();
        latestCommitHash = json.get("commit").getAsJsonObject().get("sha").getAsString();
        latestCommitAuthorName = json.get("commit").getAsJsonObject().get("commit").getAsJsonObject().get("author").getAsJsonObject().get("name").getAsString();
        latestCommitAuthorEmail = json.get("commit").getAsJsonObject().get("commit").getAsJsonObject().get("author").getAsJsonObject().get("email").getAsString();
        latestCommitMessage = json.get("commit").getAsJsonObject().get("commit").getAsJsonObject().get("message").getAsString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        latestCommitTimestamp = LocalDateTime.parse(json.get("commit").getAsJsonObject().get("commit").getAsJsonObject().get("author").getAsJsonObject().get("date").getAsString(), formatter);
        isProtected = json.get("protected").getAsBoolean();
    }

    public String getName() {
        return name;
    }

    public String getLatestCommitHash() {
        return latestCommitHash;
    }

    public String getLatestCommitAuthorName() {
        return latestCommitAuthorName;
    }

    public String getLatestCommitAuthorEmail() {
        return latestCommitAuthorEmail;
    }

    public String getLatestCommitMessage() {
        return latestCommitMessage;
    }

    public LocalDateTime getLatestCommitTimestamp() {
        return latestCommitTimestamp;
    }

    public boolean isProtected() {
        return isProtected;
    }
}
