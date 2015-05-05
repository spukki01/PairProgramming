package se.chalmers.eda397.pairprogramming.util;

import org.json.JSONException;
import org.json.JSONObject;

import se.chalmers.eda397.pairprogramming.model.Repository;

public class RepositoryMapper implements IMapper{

    @Override
    public Repository map(JSONObject jsonObject) throws JSONException {
        Repository repo = new Repository();

        repo.setId(jsonObject.getInt("id"));
        repo.setName(jsonObject.getString("name"));
        repo.setOwnerName(jsonObject.getJSONObject("owner").getString("login"));
        repo.setDescription(jsonObject.getString("description"));
        repo.setPrivate(jsonObject.getBoolean("private"));
        repo.setBranchesUrl(jsonObject.getString("branches_url"));

        return repo;
    }
}
