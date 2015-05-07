package se.chalmers.eda397.pairprogramming.model;

import se.chalmers.eda397.pairprogramming.model.Repository;

/**
 * Created by marcusisaksson on 15-04-28.
 */
public class RepoListItem {

    private Repository mRepository;

    public RepoListItem(Repository repository){
        this.mRepository = repository;
    }

    public Repository getRepository() {
        return mRepository;
    }

    public void setRepository(Repository repository) {
        this.mRepository = repository;
    }


}
