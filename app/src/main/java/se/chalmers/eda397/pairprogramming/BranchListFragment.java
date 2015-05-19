package se.chalmers.eda397.pairprogramming;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.eda397.pairprogramming.adapter.BranchListAdapter;
import se.chalmers.eda397.pairprogramming.core.ConnectionManager;
import se.chalmers.eda397.pairprogramming.core.GitHubClient;
import se.chalmers.eda397.pairprogramming.core.IGitHubClient;
import se.chalmers.eda397.pairprogramming.model.Branch;

public class BranchListFragment extends ListFragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_REPO_OWNER = "RepoOwner";
    private static final String ARG_REPO_NAME = "RepoName";

    private String mRepoOwner;
    private String mRepoName;

    private View mRootView;

    BranchListAdapter mAdapter;

    private List<Branch> mBranches = new ArrayList();



    public static BranchListFragment newInstance(String repoName, String repoOwner) {
        BranchListFragment fragment = new BranchListFragment();
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
    public BranchListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_branch, container, false);

        if (getArguments() != null) {
            mRepoOwner = getArguments().getString(ARG_REPO_OWNER);
            mRepoName = getArguments().getString(ARG_REPO_NAME);
            new BranchTask().execute(mRepoName, mRepoOwner);
        }

        mAdapter = new BranchListAdapter(inflater.getContext(), mBranches);
        this.setListAdapter(mAdapter);

        return mRootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //TODO remove 0 and fix header
        ((MainActivity) activity).onSectionAttached(0);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Branch item = (Branch)l.getItemAtPosition(position);
        ((MainActivity)this.getActivity()).openCommitsFragment(mRepoName, mRepoOwner, item.getName());
    }

    private class BranchTask extends AsyncTask<String, List<Branch>, List<Branch>> {

        private IGitHubClient mGitHubClient;
        private BranchTask() {
            mGitHubClient = new GitHubClient(new ConnectionManager());
        }

        private ProgressDialog mProgressDialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            this.mProgressDialog.setTitle(R.string.loading);
            this.mProgressDialog.setMessage(getString(R.string.pleaseWaitWhile) + " " + getString(R.string.branches));
            this.mProgressDialog.show();

            super.onPreExecute();
        }

        @Override
        protected List<Branch> doInBackground(String... args) {
            return this.mGitHubClient.findRelatedBranches(args[0], args[1]);
        }

        @Override
        protected void onPostExecute(List<Branch> result) {
            if (result.size() > 0) {
                mAdapter.clear();
                mBranches = result;

                mAdapter.addAll(mBranches);
                mAdapter.notifyDataSetChanged();

                /*ListView lw = (ListView)mRootView.findViewById(R.id.list_branches);
                lw.setAdapter(new BranchListAdapter(getActivity(), result));*/
            }

            this.mProgressDialog.dismiss();
            super.onPostExecute(result);
        }
    }
}

