package se.chalmers.eda397.pairprogramming.core;

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
import se.chalmers.eda397.pairprogramming.util.IMapper;
import se.chalmers.eda397.pairprogramming.util.RepositoryMapper;

public class GitHubClient implements IGitHubClient {

    private IConnectionManager mConnectionManager;
    private IMapper<Repository> mRepoMapper;
    private IMapper<Branch> mBranchMapper;
    private IMapper<Commit> mCommitMapper;


    public GitHubClient(IConnectionManager connectionManager) {
        this.mConnectionManager = connectionManager;
        this.mRepoMapper = new RepositoryMapper();
        this.mBranchMapper = new BranchMapper();
        this.mCommitMapper = new CommitMapper();
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

        String find_repo_url = "https://api.github.com/repos/"+repoOwner+"/"+repoName;
        String repoResponse = this.mConnectionManager.executeQuery(find_repo_url);

        try {
            JSONObject jResult = new JSONObject(repoResponse);

            Repository repo = mRepoMapper.map(jResult);

            String find_branches_url = repo.getBranchesUrl().replace("{/branch}", "");
            String branchResponse = this.mConnectionManager.executeQuery(find_branches_url);

            JSONArray jBranches = new JSONArray(branchResponse);

            for (int i=0; i< jBranches.length(); i++) {
                JSONObject jObject = jBranches.getJSONObject(i);
                list.add(mBranchMapper.map(jObject));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Commit> findCommits(String repoName, String repoOwner, String branchName) {
        List<Commit> list = new ArrayList<>();
        // https://api.github.com/repos/spukki01/pairprogramming/commits?sha=feat/push-notifications
        String fetch_commits = "https://api.github.com/repos/" + repoOwner + "/" + repoName + "/commits?sha=" + branchName;
        String repoResponse = this.mConnectionManager.executeQuery(fetch_commits);
        try {
            JSONArray jResultArray = new JSONArray(repoResponse);
            for (int i=0; i<jResultArray.length(); i++) {
                JSONObject jObject = jResultArray.getJSONObject(i);
                list.add(mCommitMapper.map(jObject));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

}
