package se.chalmers.eda397.pairprogramming.util;

import android.content.Context;
import android.content.SharedPreferences;

import se.chalmers.eda397.pairprogramming.model.Repository;

/**
 * TODO For now, only one Repository can be saved.
 */
public class RepositoryStorage implements IStorage<Repository> {
    public static final String REPOSITORY_PREFS = "Repository Prefs";
    private static final String NO_REPO_FOUND = "NO_REPO_FOUND";
    private static RepositoryStorage instance = null;

    private RepositoryStorage() {

    }

    public static RepositoryStorage getInstance() {
        if (instance == null){
            instance = new RepositoryStorage();
        }
        return instance;
    }

    @Override
    public void store(Repository repository, Context context) {
        SharedPreferences.Editor editor
                = context.getSharedPreferences(REPOSITORY_PREFS, Context.MODE_PRIVATE).edit();
        editor.putString("repoName", repository.getName());
        editor.putString("owner", repository.getOwner());
        editor.commit();
    }

    @Override
    public Repository fetch(Context context) {
        Repository repository = new Repository();
        SharedPreferences prefs = context.getSharedPreferences(REPOSITORY_PREFS, Context.MODE_PRIVATE);
        repository.setName(prefs.getString("repoName", NO_REPO_FOUND));
        repository.setOwner(prefs.getString("owner", NO_REPO_FOUND));
        return repository;
    }
}
