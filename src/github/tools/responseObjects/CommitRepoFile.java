package github.tools.responseObjects;

public class CommitRepoFile {
    private String fileName;
    private int additions;
    private int deletions;
    private int totalChanges;
    private String fileUrl;

    public CommitRepoFile(String fileName, int additions, int deletions, int totalChanges, String fileUrl) {
        this.fileName = fileName;
        this.additions = additions;
        this.deletions = deletions;
        this.totalChanges = totalChanges;
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
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

    public String getFileUrl() {
        return fileUrl;
    }
}
