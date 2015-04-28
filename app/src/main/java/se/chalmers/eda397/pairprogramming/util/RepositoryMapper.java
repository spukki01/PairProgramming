package se.chalmers.eda397.pairprogramming.util;

import org.json.JSONException;
import org.json.JSONObject;

import se.chalmers.eda397.pairprogramming.model.Repository;

public class RepositoryMapper implements IMapper{

    @Override
    public Repository map(JSONObject jsonObject) throws JSONException {
        Repository repo = new Repository();
        repo.setDescription(jsonObject.getString("description"));
        repo.setId(jsonObject.getInt("id"));
        repo.setName(jsonObject.getString("name"));
        repo.setPrivate(jsonObject.getBoolean("private"));
        return repo;
    }
}
