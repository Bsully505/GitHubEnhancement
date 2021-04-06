package github.tools.responseObjects;

import java.time.LocalDateTime;

public class Commit {
    private String commitHash;
    private String commitUrl;
    private String commitAuthorName;
    private String commitAuthorEmail;
    private LocalDateTime commitDate;
    private String commitMessage;

    public Commit(String commitHash, String commitUrl, String commitAuthorName, String commitAuthorEmail, LocalDateTime commitDate, String commitMessage) {
        this.commitHash = commitHash;
        this.commitUrl = commitUrl;
        this.commitAuthorName = commitAuthorName;
        this.commitAuthorEmail = commitAuthorEmail;
        this.commitDate = commitDate;
        this.commitMessage = commitMessage;
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
}
