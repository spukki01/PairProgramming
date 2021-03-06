package se.chalmers.eda397.pairprogramming.core;

import android.content.Context;

import java.util.List;

import se.chalmers.eda397.pairprogramming.model.Branch;
import se.chalmers.eda397.pairprogramming.model.Commit;
import se.chalmers.eda397.pairprogramming.model.Repository;

public interface IGitHubClient {

    List<Repository> findRepositories(String repoName);
    List<Branch> findRelatedBranches(String repoName, String repoOwner);
    List<Branch> findRelatedBranches(String repoName, String repoOwner, boolean includeLatestCommitDate);
    List<Commit> findCommits(String repoName, String repoOwner, String branch);
    Boolean isCommitDifferent(String repository, String owner, String branch, Context context);
    String compareBranch(String repository, String owner, String branch, String branchCompare);

}
