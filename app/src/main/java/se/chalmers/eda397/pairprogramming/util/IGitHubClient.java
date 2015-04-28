package se.chalmers.eda397.pairprogramming.util;

import se.chalmers.eda397.pairprogramming.model.Repository;

public interface IGitHubClient {

    Repository findRepository(String repoName);

}
