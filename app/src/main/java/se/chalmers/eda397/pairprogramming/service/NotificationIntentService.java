package se.chalmers.eda397.pairprogramming.service;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;

import se.chalmers.eda397.pairprogramming.core.ConnectionManager;
import se.chalmers.eda397.pairprogramming.core.GitHubClient;
import se.chalmers.eda397.pairprogramming.core.IGitHubClient;


public class NotificationIntentService extends IntentService {


    private IGitHubClient mGitHubClient;
    private LocalBroadcastManager mBroadcastManager;

    public NotificationIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        String dataString = workIntent.getDataString();

        this.mGitHubClient = new GitHubClient(new ConnectionManager());

        String latestSha = this.mGitHubClient.getLatestCommitSHA("PairProgramming", "spukki01", "master");



    }

}
