package se.chalmers.eda397.pairprogramming.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.eda397.pairprogramming.model.Repository;
import se.chalmers.eda397.pairprogramming.util.IMapper;
import se.chalmers.eda397.pairprogramming.util.RepositoryMapper;

public class GitHubClient implements IGitHubClient {

    private IConnectionManager mConnnectionManager;
    private IMapper<Repository> mMapper;

    public GitHubClient(IConnectionManager mConnnectionManager) {
        this.mConnnectionManager = mConnnectionManager;
        this.mMapper = new RepositoryMapper();
    }


    @Override
    public List<Repository> findRepositories(String repoName) {
        String url = "https://api.github.com/search/repositories?q=" + repoName + "+in:name";

        String response = this.mConnnectionManager.executeQuery(url);
        List<Repository> list = new ArrayList<Repository>();
        try {
            JSONObject jResult = new JSONObject(response);
            JSONArray jArray = jResult.getJSONArray("items");

            for (int i=0; i< jArray.length(); i++) {
                JSONObject jObject = jArray.getJSONObject(i);
                list.add(mMapper.map(jObject));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
