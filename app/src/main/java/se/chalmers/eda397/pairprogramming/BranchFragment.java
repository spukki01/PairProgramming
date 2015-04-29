package se.chalmers.eda397.pairprogramming;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.eda397.pairprogramming.core.ConnectionManager;
import se.chalmers.eda397.pairprogramming.core.GitHubClient;
import se.chalmers.eda397.pairprogramming.core.IGitHubClient;
import se.chalmers.eda397.pairprogramming.model.Branch;

public class BranchFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_REPO_OWNER = "RepoOwner";
    private static final String ARG_REPO_NAME = "RepoName";

    private String mRepoOwner;
    private String mRepoName;

    private View mRootView;


    public static BranchFragment newInstance(String repoName, String repoOwner) {
        BranchFragment fragment = new BranchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_REPO_NAME, repoName);
        args.putString(ARG_REPO_OWNER, repoOwner);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BranchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_branch, container, false);

        if (getArguments() != null) {
            mRepoOwner = getArguments().getString(ARG_REPO_OWNER);
            mRepoName = getArguments().getString(ARG_REPO_NAME);
            new BranchTask().execute(mRepoName, mRepoOwner);
        }

        return mRootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //TODO remove 0 and fix correct thingy
        ((MainActivity) activity).onSectionAttached(0);
    }

    private class BranchTask extends AsyncTask<String, List<Branch>, List<Branch>> {

        private IGitHubClient mGitHubClient;
        private BranchTask() {
            mGitHubClient = new GitHubClient(new ConnectionManager());
        }

        private ProgressDialog mProgressDialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            mProgressDialog.setTitle(R.string.loading);
            //TODO: remove hard coded string
            mProgressDialog.setMessage("Please wait while searching for branches...");
            mProgressDialog.show();

            super.onPreExecute();
        }

        @Override
        protected List<Branch> doInBackground(String... args) {
            return this.mGitHubClient.findRelatedBranches(args[0], args[1]);
        }

        @Override
        protected void onPostExecute(List<Branch> result) {
            if (result.size() > 0) {
                ListView lw = (ListView)mRootView.findViewById(R.id.list_branches);

                //TODO: remove and add custom ArrayAdapter
                List<String> temp = new ArrayList<>();
                for (int i = 0; i < result.size(); i++)
                    temp.add(result.get(i).getName());

                lw.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, temp));
            }

            mProgressDialog.dismiss();
            super.onPostExecute(result);
        }
    }
}

