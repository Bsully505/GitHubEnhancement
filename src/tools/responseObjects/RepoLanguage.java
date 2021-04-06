package github.tools.responseObjects;

public class RepoLanguage {
    private String languageName;
    private int numberOfBytesOfCodeInLanguage;

    public RepoLanguage(String languageName, int numberOfBytesOfCodeInLanguage) {
        this.languageName = languageName;
        this.numberOfBytesOfCodeInLanguage = numberOfBytesOfCodeInLanguage;
    }

    public String getLanguageName() {
        return languageName;
    }

    public int getNumberOfBytesOfCodeInLanguage() {
        return numberOfBytesOfCodeInLanguage;
    }
}
