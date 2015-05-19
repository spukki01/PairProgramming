package se.chalmers.eda397.pairprogramming.core;

import java.util.List;

import se.chalmers.eda397.pairprogramming.model.Branch;
import se.chalmers.eda397.pairprogramming.model.Commit;
import se.chalmers.eda397.pairprogramming.model.Repository;

public interface IGitHubClient {

    List<Repository> findRepositories(String repoName);
    List<Branch> findRelatedBranches(String repoName, String repoOwner);
    List<Commit> findCommits(String repoName, String repoOwner, String branch);
    Boolean isCommitDifferent(String repository, String owner, String branch);
    String compareBranch(String repository, String owner, String branch, String branchCompare);

}
