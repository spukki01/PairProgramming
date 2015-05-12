package se.chalmers.eda397.pairprogramming;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import se.chalmers.eda397.pairprogramming.adapter.SubscribedRepositoryAdapter;
import se.chalmers.eda397.pairprogramming.model.Repository;
import se.chalmers.eda397.pairprogramming.util.RepositoryStorage;


public class SubscribedRepositoriesFragment extends ListFragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private View mRootView = null;


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
        Repository item = (Repository)l.getItemAtPosition(position);
        ((MainActivity)getActivity()).openRepositoryFragment(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_subscribed_repositories, container, false);

        List<Repository> subscribedRepositories = RepositoryStorage.getInstance().fetchAll(getActivity());

        SubscribedRepositoryAdapter adapter = new SubscribedRepositoryAdapter(inflater.getContext(), subscribedRepositories);
        this.setListAdapter(adapter);

        return mRootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

}