package se.chalmers.eda397.pairprogramming.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.eda397.pairprogramming.MainActivity;
import se.chalmers.eda397.pairprogramming.R;
import se.chalmers.eda397.pairprogramming.adapter.RepoListAdapter;
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

    ArrayAdapter<Repository> mAdapter;

    private List<Repository> mRepositories = new ArrayList();

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
        Repository item = (Repository)l.getItemAtPosition(position);
        ((MainActivity)this.getActivity()).openRepositoryFragment(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_repository_search, container, false);

        final Button button = (Button) mRootView.findViewById(R.id.repo_search_button);
        button.setOnClickListener(this);

        mAdapter = new RepoListAdapter(inflater.getContext(), mRepositories);
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

        if (repoName.length() > 0) {
            new RepositoryTask().execute(repoName);
        }
        else {
            Toast.makeText(getActivity(), "Please add a search criteria.", Toast.LENGTH_SHORT).show();
        }

        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }

    private class RepositoryTask extends AsyncTask<String, List<Repository>, List<Repository>> {

        private IGitHubClient mGitHubClient;
        private ProgressDialog mProgressDialog = new ProgressDialog(getActivity());

        private RepositoryTask () {
            mGitHubClient = new GitHubClient(new ConnectionManager());
        }

        @Override
        protected void onPreExecute() {
            this.mProgressDialog.setTitle(R.string.loading);
            this.mProgressDialog.setMessage(getString(R.string.pleaseWaitWhile) + " " + getString(R.string.repositories));
            this.mProgressDialog.show();

            super.onPreExecute();
        }

        @Override
        protected List<Repository> doInBackground(String... repoName) {
            return this.mGitHubClient.findRepositories(repoName[0]);
        }

        @Override
        protected void onPostExecute(List<Repository> result) {
            if (result.size()>0) {
                mAdapter.clear();
                mRepositories = result;

                mAdapter.addAll(mRepositories);
                mAdapter.notifyDataSetChanged();
            }
            this.mProgressDialog.dismiss();
            super.onPostExecute(result);
        }
    }

}