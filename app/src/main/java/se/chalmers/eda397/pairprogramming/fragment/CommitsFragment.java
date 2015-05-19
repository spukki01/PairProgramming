package se.chalmers.eda397.pairprogramming.fragment;

import android.app.Activity;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.eda397.pairprogramming.MainActivity;
import se.chalmers.eda397.pairprogramming.R;
import se.chalmers.eda397.pairprogramming.adapter.CommitListAdapter;
import se.chalmers.eda397.pairprogramming.core.ConnectionManager;
import se.chalmers.eda397.pairprogramming.core.GitHubClient;
import se.chalmers.eda397.pairprogramming.core.IGitHubClient;
import se.chalmers.eda397.pairprogramming.model.Commit;


public class CommitsFragment extends ListFragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_REPO_OWNER = "RepoOwner";
    private static final String ARG_REPO_NAME= "RepoName";
    private static final String ARG_BRANCH_NAME = "BranchName";

    private View mRootView;

    private CommitListAdapter mAdapter;

    private List<Commit> mCommits = new ArrayList();


    public static CommitsFragment newInstance(String repoName, String repoOwner, String branchName) {
        CommitsFragment fragment = new CommitsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_REPO_OWNER, repoOwner);
        args.putString(ARG_REPO_NAME, repoName);
        args.putString(ARG_BRANCH_NAME, branchName);
        fragment.setArguments(args);
        return fragment;
    }

    public CommitsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_branch, container, false);

        if (getArguments() != null) {
            String repoOwner = getArguments().getString(ARG_REPO_OWNER);
            String repoName = getArguments().getString(ARG_REPO_NAME);
            String branchName = getArguments().getString(ARG_BRANCH_NAME);
            new CommitsTask().execute(repoName, repoOwner, branchName);
        }

        mAdapter = new CommitListAdapter(inflater.getContext(), mCommits);
        this.setListAdapter(mAdapter);

        return mRootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Commit item = (Commit)l.getItemAtPosition(position);
        Toast.makeText(this.getActivity().getApplicationContext(), "Committer: "
                + item.getCommitter()
                + "\n" + "Message: " + item.getMessage()
                + "\n" + item.getDate().toString(), Toast.LENGTH_LONG ).show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(0);
    }

    private class CommitsTask extends AsyncTask<String, List<Commit>, List<Commit>> {

        private IGitHubClient mGitHubClient;
        private CommitsTask() {
            mGitHubClient = new GitHubClient(new ConnectionManager());
        }

        private ProgressDialog mProgressDialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            mProgressDialog.setTitle(R.string.loading);
            mProgressDialog.setMessage(getString(R.string.pleaseWaitWhile) + " " + getString(R.string.commits));
            mProgressDialog.show();

            super.onPreExecute();
        }

        @Override
        protected List<Commit> doInBackground(String... args) {
            return this.mGitHubClient.findCommits(args[0], args[1], args[2]);
        }

        @Override
        protected void onPostExecute(List<Commit> result) {
            if (result.size() > 0) {
                mAdapter.clear();
                mCommits = result;

                mAdapter.addAll(mCommits);
                mAdapter.notifyDataSetChanged();
            }

            mProgressDialog.dismiss();
            super.onPostExecute(result);
        }
    }

}
