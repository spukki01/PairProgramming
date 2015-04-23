package se.chalmers.eda397.pairprogramming;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends ActionBarActivity implements View.OnClickListener,
        NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        //final Button button = (Button) findViewById(R.id.repo_search);
        //button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        /*EditText input = (EditText)findViewById(R.id.repo_input);
        String repoName = input.getText().toString();

        String url = "https://api.github.com/search/repositories?q=" + repoName + "+in:name";
        new RestClient().execute(url);

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch(position) {
            default:
            case 0:
                fragment = HomeFragment.newInstance(1);
                break;
            case 1:
                fragment = PlanningPokerFragment.newInstance(2);
                break;
            case 2:
                fragment=RepositorySearchFragment.newInstance(3);
                break;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.drawer_item_home);
                break;
            case 2:
                mTitle = getString(R.string.drawer_item_planning_poker);
                break;
            case 3:
                mTitle = getString(R.string.drawer_item_repository_search);
                break;

        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
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
            TextView text = (TextView)findViewById(R.id.repo_text);

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
*/



}