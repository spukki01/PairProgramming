package se.chalmers.eda397.pairprogramming.fragment;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

import se.chalmers.eda397.pairprogramming.MainActivity;
import se.chalmers.eda397.pairprogramming.R;
import se.chalmers.eda397.pairprogramming.model.Repository;
import se.chalmers.eda397.pairprogramming.util.RepositoryStorage;


public class RepositoryFragment extends Fragment implements View.OnClickListener{
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

        List<Repository> repos = RepositoryStorage.getInstance().fetchAll(getActivity());
        for (int i=0; i<repos.size(); i++) {

            Repository repo = repos.get(i);

            if (repo.getId() == mRepository.getId() &&
                    repo.getName().equals(mRepository.getName()) &&
                    repo.getOwnerName().equals(mRepository.getOwnerName()))
            {
                ToggleButton commitToggle = (ToggleButton) rootView.findViewById(R.id.commit_togglebutton);
                commitToggle.setOnClickListener(this);
                commitToggle.setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.commit_toggle_text).setVisibility(View.VISIBLE);

                ToggleButton mergeToggle = (ToggleButton) rootView.findViewById(R.id.merge_togglebutton);
                mergeToggle.setOnClickListener(this);
                mergeToggle.setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.merge_toggle_text).setVisibility(View.VISIBLE);
                break;
            }
        }

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

    @Override
    public void onClick(View v) {
        boolean isChecked = ((ToggleButton)v).isChecked();

        switch (v.getId()) {
            case R.id.commit_togglebutton:
                Toast.makeText(getActivity(), "Commit: " + isChecked, Toast.LENGTH_SHORT).show();
                break;
            case R.id.merge_togglebutton:
                Toast.makeText(getActivity(), "Merge: " + isChecked, Toast.LENGTH_SHORT).show();
                break;
        }


    }
}