package se.chalmers.eda397.pairprogramming;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
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
        String branch = "master";
        String repo = "PairProgramming";
        String owner = "spukki01";
        int id = 001;
        Boolean branchChange = this.mGitHubClient.isCommitDifferent(repo, owner, branch);
        if(branchChange) {
            this.mHandler.post(new DisplayToast(this, "Commit to: " + repo + "/" + owner + "/" + branch));
            sendNotification(id, "Commit to: " + repo + "/" + owner + "/" + branch);
        }

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

    private void sendNotification(int id, String text)
    {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("New Commit")
                        .setContentText(text);
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(id, mBuilder.build());
    }

}
