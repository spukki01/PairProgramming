package se.chalmers.eda397.pairprogramming;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.eda397.pairprogramming.core.ConnectionManager;
import se.chalmers.eda397.pairprogramming.core.GitHubClient;
import se.chalmers.eda397.pairprogramming.core.IGitHubClient;
import se.chalmers.eda397.pairprogramming.model.Repository;


public class RepositorySearchFragment extends ListFragment implements View.OnClickListener{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private View mRootView = null;

    ArrayAdapter<RepoListItem> mAdapter;

    private List<RepoListItem> mRepoListItems = new ArrayList();

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RepositorySearchFragment  newInstance(int sectionNumber) {
        RepositorySearchFragment  fragment = new RepositorySearchFragment ();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public RepositorySearchFragment () {
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        RepoListItem item = (RepoListItem)l.getItemAtPosition(position);
        MainActivity mainActivity = (MainActivity) this.getActivity();
        mainActivity.openRepositoryFragment(item.getRepository());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_repository_search, container, false);

        final Button button = (Button) mRootView.findViewById(R.id.repo_search_button);
        button.setOnClickListener(this);

        mAdapter = new RepoListAdapter(
                inflater.getContext(), mRepoListItems);
        this.setListAdapter(mAdapter);

        return mRootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onClick(View v) {
        EditText input = (EditText)mRootView.findViewById(R.id.repo_input);
        String repoName = input.getText().toString();

        new RepositoryTask().execute(repoName);

        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }

    public void addRepoOnClick(View v){

    }

    private class RepositoryTask extends AsyncTask<String, List<Repository>, List<Repository>> {

        private IGitHubClient mGitHubClient;
        private RepositoryTask () {
            mGitHubClient = new GitHubClient(new ConnectionManager());
        }
        @Override
        protected List<Repository> doInBackground(String... repoName) {
            return this.mGitHubClient.findRepositories(repoName[0]);
        }

        @Override
        protected void onPostExecute(List<Repository> result) {
            if (result.size()>0) {

                mAdapter.clear();
                mRepoListItems.clear();

                for (Repository r : result) {
                    mRepoListItems.add(new RepoListItem(r));
                }
                mAdapter.addAll(mRepoListItems);
                mAdapter.notifyDataSetChanged();
            }
            super.onPostExecute(result);
        }
    }

}