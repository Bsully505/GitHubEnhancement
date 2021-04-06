package github.tools.responseObjects;

import com.google.gson.JsonArray;
import java.util.ArrayList;

public class ListBranchesInRepoResponse extends ApiArrayResponse {

    private ArrayList<Branch> branches = new ArrayList<>();

    public ListBranchesInRepoResponse(JsonArray json) {
        super(json);
        mapParams(json);
    }

    public void mapParams(JsonArray json) {
        for (int i = 0; i < json.size(); i++) {
            String name = json.get(i).getAsJsonObject().get("name").getAsString();
            String latestCommitHash = json.get(i).getAsJsonObject().get("commit").getAsJsonObject().get("sha").getAsString();
            boolean isProtected = json.get(i).getAsJsonObject().get("protected").getAsBoolean();
            branches.add(new Branch(name, latestCommitHash, isProtected));
        }
    }

    public ArrayList<Branch> getBranches() {
        return branches;
    }
}

