package se.chalmers.eda397.pairprogramming;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import se.chalmers.eda397.pairprogramming.model.Repository;
import se.chalmers.eda397.pairprogramming.util.ConnectionManager;
import se.chalmers.eda397.pairprogramming.util.GitHubClient;
import se.chalmers.eda397.pairprogramming.util.IGitHubClient;


public class RepositorySearchFragment extends Fragment implements View.OnClickListener{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private View mRootView = null;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         mRootView = inflater.inflate(R.layout.fragment_repository_search, container, false);

        final Button button = (Button) mRootView.findViewById(R.id.repo_search_button);
        button.setOnClickListener(this);

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

        new RestClient().execute(repoName);

        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }


    private class RestClient extends AsyncTask<String, Repository, Repository> {

        private IGitHubClient mGitHubClient;

        private RestClient () {
            mGitHubClient = new GitHubClient(new ConnectionManager());
        }


        @Override
        protected Repository doInBackground(String... repoName) {
            Repository repo = this.mGitHubClient.findRepository(repoName[0]);
            return repo;
        }

        protected void onPostExecute(Repository result) {
            TextView text = (TextView)mRootView.findViewById(R.id.repo_text);

            try {
                String parsedString = "";
                JSONObject jResult = new JSONObject("");//result);
                JSONArray jArray = jResult.getJSONArray("items");

                for (int i=0; i< jArray.length(); i++) {
                    JSONObject jObject = jArray.getJSONObject(i);
                    parsedString = parsedString + System.getProperty("line.separator") + jObject.getInt("id") + ": " + jObject.getString("full_name");
                }

                text.setText(parsedString);

            } catch (JSONException e) {
                e.printStackTrace();
                text.setText(e.getMessage());
            }

            super.onPostExecute(result);
        }
    }

}