package se.chalmers.eda397.pairprogramming.util;

import se.chalmers.eda397.pairprogramming.model.Repository;

public class GitHubClient implements IGitHubClient {

    private IConnectionManager mConnnectionManager;

    public GitHubClient(IConnectionManager mConnnectionManager) {
        this.mConnnectionManager = mConnnectionManager;
    }


    @Override
    public Repository findRepository(String repoName) {
        String url = "https://api.github.com/search/repositories?q=" + repoName + "+in:name";

        String response = this.mConnnectionManager.executeStatement(url);


        //TODO: mapp repsonse to object;

        return new Repository();
    }
}
