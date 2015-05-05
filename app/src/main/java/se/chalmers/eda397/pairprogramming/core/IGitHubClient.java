package se.chalmers.eda397.pairprogramming.core;

import java.util.List;

import se.chalmers.eda397.pairprogramming.model.Branch;
import se.chalmers.eda397.pairprogramming.model.Repository;

public interface IGitHubClient {

    List<Repository> findRepositories(String repoName);
    List<Branch> findRelatedBranches(String repoName, String repoOwner);

    String getLatestCommitSHA(String repository, String owner, String branch);
}
