package se.chalmers.eda397.pairprogramming.core;

import java.util.List;

import se.chalmers.eda397.pairprogramming.model.Repository;

public interface IGitHubClient {

    List<Repository> findRepositories(String repoName);

}
