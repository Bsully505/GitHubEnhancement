package github.tools.client;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BasicAuth {
    private String user;
    private String password;

    public BasicAuth(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String toAuthHeader() {
        String auth = user + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        return "Basic " + new String(encodedAuth);
    }
}
