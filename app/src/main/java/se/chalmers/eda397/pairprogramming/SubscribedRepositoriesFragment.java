package se.chalmers.eda397.pairprogramming;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import se.chalmers.eda397.pairprogramming.model.Repository;
import se.chalmers.eda397.pairprogramming.util.RepositoryStorage;


public class SubscribedRepositoriesFragment extends ListFragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private View mRootView = null;

    ArrayAdapter<RepoListItem> mAdapter;
    private List<RepoListItem> mRepoListItems = new ArrayList();

    public static SubscribedRepositoriesFragment  newInstance(int sectionNumber) {
        SubscribedRepositoriesFragment fragment = new SubscribedRepositoriesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public SubscribedRepositoriesFragment () {
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        RepoListItem item = (RepoListItem)l.getItemAtPosition(position);
        MainActivity mainActivity = (MainActivity) this.getActivity();
        mainActivity.openRepositoryFragment(item.getRepository());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_subscribed_repositories, container, false);

        mAdapter = new RepoListAdapter(inflater.getContext(), mRepoListItems);
        this.setListAdapter(mAdapter);

        fillList();

        return mRootView;
    }

    private void fillList(){
        mAdapter.clear();
        mRepoListItems.clear();

        List<Repository> subscribedRepos = RepositoryStorage.getInstance().fetchAll(getActivity());
        for (Repository r : subscribedRepos) {
            mRepoListItems.add(new RepoListItem(r));
        }

        mAdapter.addAll(mRepoListItems);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

}