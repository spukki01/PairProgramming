package se.chalmers.eda397.pairprogramming.core;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.eda397.pairprogramming.model.Branch;
import se.chalmers.eda397.pairprogramming.model.Commit;
import se.chalmers.eda397.pairprogramming.model.Repository;
import se.chalmers.eda397.pairprogramming.util.BranchMapper;
import se.chalmers.eda397.pairprogramming.util.CommitMapper;
import se.chalmers.eda397.pairprogramming.util.RepositoryMapper;

public class GitHubClient implements IGitHubClient {

    private IConnectionManager mConnectionManager;

    public GitHubClient(IConnectionManager connectionManager) {
        this.mConnectionManager = connectionManager;
    }

    @Override
    public List<Repository> findRepositories(String repoName) {
        String url = "https://api.github.com/search/repositories?q=" + repoName + "+in:name";

        String response = this.mConnectionManager.select(url);
        List<Repository> list = new ArrayList<>();
        try {
            JSONObject jResult = new JSONObject(response);
            JSONArray jArray = jResult.getJSONArray("items");

            for (int i=0; i< jArray.length(); i++) {
                JSONObject jObject = jArray.getJSONObject(i);
                list.add(RepositoryMapper.getInstance().map(jObject));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Branch> findRelatedBranches(String repoName, String repoOwner) {
        List<Branch> list = new ArrayList<>();

        String find_repo_url = "https://api.github.com/repos/"+repoOwner+"/"+repoName;
        String repoResponse = this.mConnectionManager.select(find_repo_url);

        try {
            JSONObject jResult = new JSONObject(repoResponse);

            Repository repo = RepositoryMapper.getInstance().map(jResult);

            String find_branches_url = repo.getBranchesUrl().replace("{/branch}", "");
            String branchResponse = this.mConnectionManager.select(find_branches_url);

            JSONArray jBranches = new JSONArray(branchResponse);

            for (int i=0; i< jBranches.length(); i++) {
                JSONObject jObject = jBranches.getJSONObject(i);
                list.add(BranchMapper.getInstance().map(jObject));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Commit> findCommits(String repoName, String repoOwner, String branchName) {
        String url = "https://api.github.com/repos/" + repoOwner + "/" + repoName + "/commits?sha=" + branchName;
        String repoResponse = this.mConnectionManager.select(url);

        List<Commit> list = new ArrayList<>();
        try {
            JSONArray jResultArray = new JSONArray(repoResponse);

            for (int i=0; i<jResultArray.length(); i++) {
                JSONObject jObject = jResultArray.getJSONObject(i);
                list.add(CommitMapper.getInstance().map(jObject));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Boolean isCommitDifferent(String repository, String owner, String branch, Context context)
    {
        String latestCommitSHA = getLatestCommitSHA(repository, owner, branch);

        boolean isDifferent = false;
        try {
            SharedPreferences sharedPref = context.getSharedPreferences("gitSavedData", Context.MODE_PRIVATE);

            String defaultValue = "error";
            String previousSHA = sharedPref.getString(branch, defaultValue);

            isDifferent = !previousSHA.equals(latestCommitSHA);

            if (isDifferent) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(branch, latestCommitSHA);
                editor.commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return isDifferent;
    }

    @Override
    public String compareBranch(String repository, String owner, String branch, String branchCompare) {
        String fileName = "";
        String url = "https://api.github.com/repos/" + owner + "/" + repository + "/compare/" + branch + "..." + branchCompare;
        String response = this.mConnectionManager.select(url);

        try {
            JSONObject jResult = new JSONObject(response);
            if (jResult.has("files")) {
                JSONArray files = jResult.getJSONArray("files");
                for (int i=0; i < files.length(); i++) {
                    fileName += files.getJSONObject(i).getString("filename") + "/n";
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return fileName;
    }

    private String getLatestCommitSHA(String repository, String owner, String branch) {
        String commitSHA = "";
        String url = "https://api.github.com/repos/" + owner + "/" + repository + "/branches/" + branch;
        String repoResponse = this.mConnectionManager.select(url);

        try {
            JSONObject jResult = new JSONObject(repoResponse);
            commitSHA = jResult.getJSONObject("commit").getString("sha");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return commitSHA;
    }

}