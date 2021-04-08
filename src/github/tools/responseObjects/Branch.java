package github.tools.responseObjects;

public class Branch {
    private String name;
    private String latestCommitHash;
    private boolean isProtected;

    public Branch(String name, String latestCommitHash, boolean isProtected) {
        this.name = name;
        this.latestCommitHash = latestCommitHash;
        this.isProtected = isProtected;
    }

    public String getName() {
        return name;
    }

    public String getLatestCommitHash() {
        return latestCommitHash;
    }

    public boolean isProtected() {
        return isProtected;
    }
}
