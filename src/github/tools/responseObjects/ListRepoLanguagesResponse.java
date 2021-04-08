package github.tools.responseObjects;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Set;

public class ListRepoLanguagesResponse extends ApiObjectResponse {

    private ArrayList<RepoLanguage> languages = new ArrayList<>();

    public ListRepoLanguagesResponse(JsonObject json) {
        super(json);
        mapParams(json);
    }

    public void mapParams(JsonObject json) {
        Set<String> keys = json.keySet();
        keys.forEach(key -> {
            String languageName = key;
            int numberOfBytesOfCodeInLanguage = json.get(key).getAsInt();
            languages.add(new RepoLanguage(languageName, numberOfBytesOfCodeInLanguage));
        });
    }

    public ArrayList<RepoLanguage> getLanguages() {
        return languages;
    }
}
