package github.tools.responseObjects;

public class Collaborator {
    private String username;
    private String userUrl;

    public Collaborator(String username, String userUrl) {
        this.username = username;
        this.userUrl = userUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getUserUrl() {
        return userUrl;
    }
}
