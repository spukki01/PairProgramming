package se.chalmers.eda397.pairprogramming;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import se.chalmers.eda397.pairprogramming.core.ConnectionManager;
import se.chalmers.eda397.pairprogramming.core.GitHubClient;
import se.chalmers.eda397.pairprogramming.core.IGitHubClient;
import se.chalmers.eda397.pairprogramming.model.Commit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CommitsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CommitsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommitsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_REPO_OWNER = "RepoOwner";
    private static final String ARG_REPO_NAME= "RepoName";
    private static final String ARG_BRANCH_NAME = "BranchName";

    // TODO: Rename and change types of parameters
    private String mRepoOwner;
    private String mRepoName;
    private String mBranchName;

    private View mRootView;


    private OnFragmentInteractionListener mListener;


    // TODO: Rename and change types and number of parameters
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRepoOwner = getArguments().getString(ARG_REPO_OWNER);
            mRepoName = getArguments().getString(ARG_REPO_NAME);
            mBranchName = getArguments().getString(ARG_BRANCH_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_branch, container, false);

        if (getArguments() != null) {
            mRepoOwner = getArguments().getString(ARG_REPO_OWNER);
            mRepoName = getArguments().getString(ARG_REPO_NAME);
            mBranchName = getArguments().getString(ARG_BRANCH_NAME);
            new CommitsTask().execute(mRepoName, mRepoOwner, mBranchName);
        }

        return mRootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
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
            //TODO: remove hard coded string
            mProgressDialog.setMessage("Please wait while fetching commits...");
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
                //ListView lw = (ListView)mRootView.findViewById(R.id.list_branches);
                //lw.setAdapter(new BranchListAdapter(getActivity(), result));
            }

            mProgressDialog.dismiss();
            super.onPostExecute(result);
        }
    }

}
