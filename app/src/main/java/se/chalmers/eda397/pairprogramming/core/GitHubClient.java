package se.chalmers.eda397.pairprogramming.core;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.eda397.pairprogramming.model.Branch;
import se.chalmers.eda397.pairprogramming.model.Repository;
import se.chalmers.eda397.pairprogramming.util.BranchMapper;
import se.chalmers.eda397.pairprogramming.util.IMapper;
import se.chalmers.eda397.pairprogramming.util.RepositoryMapper;

public class GitHubClient implements IGitHubClient {

    private IConnectionManager mConnectionManager;
    private IMapper<Repository> mRepoMapper;
    private IMapper<Branch> mBranchMapper;


    public GitHubClient(IConnectionManager connectionManager) {
        this.mConnectionManager = connectionManager;
        this.mRepoMapper = new RepositoryMapper();
        this.mBranchMapper = new BranchMapper();
    }


    @Override
    public List<Repository> findRepositories(String repoName) {
        String url = "https://api.github.com/search/repositories?q=" + repoName + "+in:name";

        String response = this.mConnectionManager.executeQuery(url);
        List<Repository> list = new ArrayList<>();
        try {
            JSONObject jResult = new JSONObject(response);
            JSONArray jArray = jResult.getJSONArray("items");

            for (int i=0; i< jArray.length(); i++) {
                JSONObject jObject = jArray.getJSONObject(i);
                list.add(mRepoMapper.map(jObject));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Branch> findRelatedBranches(String repoName, String repoOwner) {
        List<Branch> list = new ArrayList<>();

        String find_repo_url = "https://api.github.com/search/repositories?q=user:"+repoOwner+"&repo:"+repoName;
        String repoResponse = this.mConnectionManager.executeQuery(find_repo_url);

        try {
            JSONObject jResult = new JSONObject(repoResponse);
            JSONArray jArray = jResult.getJSONArray("items");

            if (jArray.length() == 1) {
                Repository repo = mRepoMapper.map(jArray.getJSONObject(0));

                String find_branches_url = repo.getBranchesUrl().replace("{/branch}", "");
                String branchResponse = this.mConnectionManager.executeQuery(find_branches_url);

                JSONArray jBranches = new JSONArray(branchResponse);

                for (int i=0; i< jBranches.length(); i++) {
                    JSONObject jObject = jBranches.getJSONObject(i);
                    list.add(mBranchMapper.map(jObject));
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public String getLatestCommitSHA(String repository, String owner, String branch) {
        String commitSHA = "";
        String find_repo_url = "https://api.github.com/repos/" + owner + "/" + repository + "/branches/" + branch;
        String repoResponse = this.mConnectionManager.executeQuery(find_repo_url);

        try {
            JSONObject jResult = new JSONObject(repoResponse);
            commitSHA = jResult.getJSONObject("commit").getString("sha");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return commitSHA;
    }

    @Override
    public Boolean checkCommit(String repository, String owner, String branch)
    {
        String commitSHA = getLatestCommitSHA(repository, owner, branch);
        boolean isDifferent = false;

        try {
            Context context = applicationContextProvider.getContext();
            SharedPreferences sharedPref = context.getSharedPreferences("gitSavedData", Context.MODE_PRIVATE);
            String defaultValue = "error";
            String previousSHA = sharedPref.getString(branch, defaultValue);
            if(previousSHA.equals(commitSHA)) {
                return isDifferent;
            }
            else
            {
                isDifferent = true;
            }
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(branch, commitSHA);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isDifferent;
    }
}
