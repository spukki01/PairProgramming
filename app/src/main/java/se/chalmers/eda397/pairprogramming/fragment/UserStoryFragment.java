package se.chalmers.eda397.pairprogramming.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import se.chalmers.eda397.pairprogramming.MainActivity;
import se.chalmers.eda397.pairprogramming.R;
import se.chalmers.eda397.pairprogramming.core.ConnectionManager;
import se.chalmers.eda397.pairprogramming.core.IPivotalTrackerClient;
import se.chalmers.eda397.pairprogramming.core.PivotalTrackerClient;
import se.chalmers.eda397.pairprogramming.model.UserStory;


public class UserStoryFragment extends Fragment {

    private static final String ARG_PROJECT_ID = "param_project_id";
    private static final String ARG_STORY_ID = "param_story_id";

    private int mProjectId;
    private int mStoryId;

    private View mRootView;

    public static UserStoryFragment newInstance(int projectId, int storyId) {
        UserStoryFragment fragment = new UserStoryFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_PROJECT_ID, projectId);
        args.putInt(ARG_STORY_ID, storyId);
        fragment.setArguments(args);

        return fragment;
    }

    public UserStoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProjectId = getArguments().getInt(ARG_PROJECT_ID);
            mStoryId = getArguments().getInt(ARG_STORY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mRootView = inflater.inflate(R.layout.fragment_user_story, container, false);

        new UserStoryTask().execute(mProjectId, mStoryId);

        return mRootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //TODO Fix header id
        ((MainActivity) activity).onSectionAttached(0);
    }

    private class UserStoryTask extends AsyncTask<Integer, UserStory, UserStory> {

        private IPivotalTrackerClient mPivotalTrackerClient;
        private ProgressDialog mProgressDialog = new ProgressDialog(getActivity());

        private UserStoryTask () {
            mPivotalTrackerClient = new PivotalTrackerClient(new ConnectionManager());
        }

        @Override
        protected void onPreExecute() {
            this.mProgressDialog.setTitle(R.string.loading);
            this.mProgressDialog.setMessage(getString(R.string.pleaseWaitWhile) + " " + getString(R.string.userStory));
            this.mProgressDialog.show();

            super.onPreExecute();
        }

        @Override
        protected UserStory doInBackground(Integer... args) {
            return this.mPivotalTrackerClient.fetchUserStory(args[0], args[1]);
        }

        @Override
        protected void onPostExecute(UserStory result) {
            ((TextView)mRootView.findViewById(R.id.us_name)).setText(result.getName());
            ((TextView)mRootView.findViewById(R.id.us_type)).setText(result.getType());
            ((TextView)mRootView.findViewById(R.id.us_estimate)).setText(Integer.toString(result.getEstimate()));
            ((TextView)mRootView.findViewById(R.id.us_created)).setText(result.getCreatedDate());
            ((TextView)mRootView.findViewById(R.id.us_description)).setText(result.getDescription());

            this.mProgressDialog.dismiss();
            super.onPostExecute(result);
        }
    }


}
