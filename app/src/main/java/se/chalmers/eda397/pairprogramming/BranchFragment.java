package se.chalmers.eda397.pairprogramming;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import se.chalmers.eda397.pairprogramming.core.ConnectionManager;
import se.chalmers.eda397.pairprogramming.core.GitHubClient;
import se.chalmers.eda397.pairprogramming.core.IGitHubClient;
import se.chalmers.eda397.pairprogramming.model.Branch;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class BranchFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_REPO_OWNER = "RepoOwner";
    private static final String ARG_REPO_NAME = "RepoName";

    private String mRepoOwner;
    private String mRepoName;

    private View mRootView;

    private OnFragmentInteractionListener mListener;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mRepoOwner = getArguments().getString(ARG_REPO_OWNER);
            mRepoName = getArguments().getString(ARG_REPO_NAME);
            new BranchTask().execute(mRepoName, mRepoOwner);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_repository_search, container, false);

        return mRootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(0);
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
        public void onFragmentInteraction(String id);
    }

    private class BranchTask extends AsyncTask<String, List<Branch>, List<Branch>> {

        private IGitHubClient mGitHubClient;
        private BranchTask() {
            mGitHubClient = new GitHubClient(new ConnectionManager());
        }

        @Override
        protected List<Branch> doInBackground(String... args) {
            return this.mGitHubClient.findRelatedBranches(args[0], args[1]);
        }

        @Override
        protected void onPostExecute(List<Branch> result) {
            if (result.size()>0) {
                // TODO: Change Adapter to display your content
                ListView listView = (ListView)mRootView.findViewById(R.id.list_branches);
                listView.setAdapter(new ArrayAdapter<Branch>(getActivity(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, result));
            }
            super.onPostExecute(result);
        }
    }
}

