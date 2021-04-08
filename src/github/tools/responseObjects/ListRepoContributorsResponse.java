package github.tools.responseObjects;

import com.google.gson.JsonArray;
import java.util.ArrayList;

public class ListRepoContributorsResponse extends ApiArrayResponse {

    private ArrayList<Contributor> contributors = new ArrayList<>();

    public ListRepoContributorsResponse(JsonArray json) {
        super(json);
        mapParams(json);
    }

    public void mapParams(JsonArray json) {
        for (int i = 0; i < json.size(); i++) {
            String username = json.get(i).getAsJsonObject().get("login").getAsString();
            int numberOfContributions = json.get(i).getAsJsonObject().get("contributions").getAsInt();
            contributors.add(new Contributor(username, numberOfContributions));
        }
    }

    public ArrayList<Contributor> getContributors() {
        return contributors;
    }
}
