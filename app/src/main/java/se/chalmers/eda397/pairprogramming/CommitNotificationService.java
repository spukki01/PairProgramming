package se.chalmers.eda397.pairprogramming;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import se.chalmers.eda397.pairprogramming.core.ConnectionManager;
import se.chalmers.eda397.pairprogramming.core.GitHubClient;
import se.chalmers.eda397.pairprogramming.core.IGitHubClient;

public class CommitNotificationService extends IntentService{

    private IGitHubClient mGitHubClient;
    private Handler mHandler;

    public CommitNotificationService() {
        super("CommitNotificationService");
        this.mHandler = new Handler();
        this.mGitHubClient = new GitHubClient(new ConnectionManager());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String commitKey = this.mGitHubClient.getLatestCommitSHA("PairProgramming", "spukki01", "master");
        this.mHandler.post(new DisplayToast(this, commitKey));
    }

    private class DisplayToast implements Runnable {

        private final Context mContext;
        private String mText;

        public DisplayToast(Context mContext, String text){
            this.mContext = mContext;
            this.mText = text;
        }

        public void run(){
            Toast.makeText(this.mContext, this.mText, Toast.LENGTH_SHORT).show();
        }
    }

}
