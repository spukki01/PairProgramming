package se.chalmers.eda397.pairprogramming.core;

import junit.framework.TestCase;


import java.util.List;

import se.chalmers.eda397.pairprogramming.model.Commit;

public class GitHubClientTest extends TestCase {

    public void testCommits() {
        IGitHubClient client = new GitHubClient(new ConnectionManager());
        List<Commit> list = client.findCommits("pairprogramming", "spukki01", "master");

        assertTrue(list.size() > 0);
        for(Commit c: list) {
            assertNotNull(c);
        }
    }
}