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



public class RemoveRepositoryFavoritesFragment extends Fragment implements View.OnClickListener{
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
    public static RemoveRepositoryFavoritesFragment newInstance(int sectionNumber) {
        RemoveRepositoryFavoritesFragment fragment = new RemoveRepositoryFavoritesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public RemoveRepositoryFavoritesFragment () {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_removerepository_favorites, container, false);

        final Button button = (Button) mRootView.findViewById(R.id.remove_repo_button);
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
        EditText input = (EditText)mRootView.findViewById(R.id.repo_text);
        String repoName = input.getText().toString();

        String url = "https://api.github.com/search/repositories?q=" + repoName + "+in:name";
        new RestClient().execute(url);

        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }


    private class RestClient extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString;

            try {
                response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();

                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);

                    responseString = out.toString();

                    out.close();
                } else{
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }

            } catch (Exception e) {
                e.printStackTrace();
                responseString = e.getMessage();
            }

            return responseString;
        }

        protected void onPostExecute(String result) {
            TextView text = (TextView)mRootView.findViewById(R.id.repo_text);

            try {
                String parsedString = "";
                JSONObject jResult = new JSONObject(result);
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