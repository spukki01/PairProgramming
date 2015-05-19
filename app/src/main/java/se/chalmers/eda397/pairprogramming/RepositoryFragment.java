package se.chalmers.eda397.pairprogramming;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import se.chalmers.eda397.pairprogramming.model.Repository;


public class RepositoryFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_REPOSITORY = "arg_repository";

    private Repository mRepository;

    public static RepositoryFragment newInstance(Repository repo) {
        RepositoryFragment fragment = new RepositoryFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_REPOSITORY, repo);
        fragment.setArguments(args);

        return fragment;
    }

    public RepositoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRepository = (Repository)getArguments().getSerializable(ARG_REPOSITORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_repository, container, false);

        //Set text views
        ((TextView) rootView.findViewById(R.id.repo_name_text)).setText(mRepository.getName());
        ((TextView) rootView.findViewById(R.id.repo_owner_text)).setText("Owner: " + mRepository.getOwnerName());
        ((TextView) rootView.findViewById(R.id.repo_desc)).setText(mRepository.getDescription());

        //Add branch list
        Fragment branchFragment = BranchListFragment.newInstance(mRepository.getName(), mRepository.getOwnerName());
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.branch_fragment_container, branchFragment).commit();
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //TODO: fix header title
        ((MainActivity) activity).onSectionAttached(0);
    }

}