package se.chalmers.eda397.pairprogramming;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;

import se.chalmers.eda397.pairprogramming.core.ExceptionHandler;
import se.chalmers.eda397.pairprogramming.fragment.CommitsFragment;
import se.chalmers.eda397.pairprogramming.fragment.HomeFragment;
import se.chalmers.eda397.pairprogramming.fragment.NavigationDrawerFragment;
import se.chalmers.eda397.pairprogramming.fragment.PlanningPokerFragment;
import se.chalmers.eda397.pairprogramming.fragment.RepositoryFragment;
import se.chalmers.eda397.pairprogramming.fragment.RepositorySearchFragment;
import se.chalmers.eda397.pairprogramming.fragment.SubscribedRepositoriesFragment;
import se.chalmers.eda397.pairprogramming.fragment.TimerFragment;
import se.chalmers.eda397.pairprogramming.fragment.UserStoryFragment;
import se.chalmers.eda397.pairprogramming.model.Repository;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    protected PendingIntent mPendingIntent;
    protected AlarmManager mAlarmManager;

    protected static int ALARM_INTERVAL = 1000 * 30 * 1; //milliseconds * seconds * minutes

    @Override
    protected void onStart() {
        super.onStart();
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), ALARM_INTERVAL, mPendingIntent);
    }

    @Override
    protected void onStop() {
        this.mAlarmManager.cancel(this.mPendingIntent);
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        mAlarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(),  CommitNotificationReceiver.class);
        mPendingIntent = PendingIntent.getBroadcast(this, 1234567, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
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
        Fragment fragment;
        switch(position) {
            default:
            case 0:
                fragment = HomeFragment.newInstance(1);
                break;
            case 1:
                fragment = RepositorySearchFragment.newInstance(2);
                break;
            case 2:
                fragment = SubscribedRepositoriesFragment.newInstance(3);
                break;
            case 3:
                fragment = PlanningPokerFragment.newInstance(4);
                break;
            case 4:
                fragment = TimerFragment.newInstance(5);
                break;
        }

        getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.drawer_item_home);
                break;
            case 2:
                mTitle = getString(R.string.drawer_item_repository_search);
                break;
            case 3:
                mTitle = getString(R.string.drawer_item_subscrided_repo);
                break;
            case 4:
                mTitle = getString(R.string.drawer_item_planning_poker);
                break;
            case 5:
                mTitle = getString(R.string.drawer_item_timer);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    /*
    * Is called form RepositorySearchFragment when clicking a list item.
    */
    public void openRepositoryFragment(Repository repository){
        RepositoryFragment newFragment = RepositoryFragment.newInstance(repository);

        getFragmentManager().beginTransaction()
                .replace(R.id.container, newFragment)
                .addToBackStack(null)
                .commit();
    }

    /*
    * Is called form BranchListFragment when clicking a list item.
    */
    public void openCommitsFragment(String repoName, String repoOwner, String branchName){
        CommitsFragment newFragment = CommitsFragment.newInstance(repoName, repoOwner, branchName);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, newFragment)
                .addToBackStack(null)
                .commit();
    }

    public void openUserStoryFragment(long projectId, long userStoryId) {
        UserStoryFragment newFragment = UserStoryFragment.newInstance(projectId, userStoryId);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, newFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }


}