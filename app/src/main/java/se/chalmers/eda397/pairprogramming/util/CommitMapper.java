package se.chalmers.eda397.pairprogramming.util;

import org.json.JSONException;
import org.json.JSONObject;

import se.chalmers.eda397.pairprogramming.model.Commit;

public class CommitMapper implements IMapper<Commit> {

    private static CommitMapper instance;

    public static CommitMapper getInstance() {
        if (instance == null){
            instance = new CommitMapper();
        }
        return instance;
    }


    @Override
    public Commit map(JSONObject jsonObject) throws JSONException {
        Commit commit = new Commit();
        JSONObject commitObject = jsonObject.getJSONObject("commit");

        commit.setSha(jsonObject.getString("sha"));

        commit.setMessage(jsonObject.getJSONObject("commit").getString("message"));
        commit.setCommitter(commitObject.getJSONObject("author").getString("name"));
        commit.setDate(commitObject.getJSONObject("author").getString("date"));

        return commit;
    }

}
