package github.tools.responseObjects;

public class Contributor {
    private String username;
    private int numberOfContributions;

    public Contributor(String username, int numberOfContributions) {
        this.username = username;
        this.numberOfContributions = numberOfContributions;
    }

    public String getUsername() {
        return username;
    }

    public int getNumberOfContributions() {
        return numberOfContributions;
    }
}
