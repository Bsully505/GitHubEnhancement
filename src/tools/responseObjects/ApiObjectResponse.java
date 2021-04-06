package github.tools.responseObjects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class ApiObjectResponse {
    private JsonObject json;

    public ApiObjectResponse(JsonObject json) {
        this.json = json;
    }

    public JsonObject getJson() {
        return json;
    }

    public void printJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String formatted = gson.toJson(json);
        System.out.println(formatted);
    }
}
