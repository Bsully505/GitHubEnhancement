package github.tools.responseObjects;

import com.google.gson.JsonArray;

import java.util.ArrayList;

public class ListRepoCollaboratorsResponse extends ApiArrayResponse {

    private ArrayList<Collaborator> collaborators = new ArrayList<>();

    public ListRepoCollaboratorsResponse(JsonArray json) {
        super(json);
        mapParams(json);
    }

    public void mapParams(JsonArray json) {
        for (int i = 0; i < json.size(); i++) {
            String username = json.get(i).getAsJsonObject().get("login").getAsString();
            String userUrl = json.get(i).getAsJsonObject().get("html_url").getAsString();
            collaborators.add(new Collaborator(username, userUrl));
        }
    }
}
