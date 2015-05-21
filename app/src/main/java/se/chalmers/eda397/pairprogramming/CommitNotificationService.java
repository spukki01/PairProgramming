package se.chalmers.eda397.pairprogramming;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import se.chalmers.eda397.pairprogramming.core.ConnectionManager;
import se.chalmers.eda397.pairprogramming.core.GitHubClient;
import se.chalmers.eda397.pairprogramming.core.IGitHubClient;
import se.chalmers.eda397.pairprogramming.model.Branch;
import se.chalmers.eda397.pairprogramming.model.Repository;
import se.chalmers.eda397.pairprogramming.util.RepositoryStorage;

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
        List<Repository> subscribedRepositories = RepositoryStorage.getInstance().fetchAll(getApplicationContext());

        for(int i = 0; i < subscribedRepositories.size(); i++) {
            Repository repo = subscribedRepositories.get(i);

            if (!repo.isCommitNotificationOn() && !repo.isMergeNotificationOn()) continue;

            String repoName = repo.getName();
            String owner = repo.getOwnerName();

            List<Branch> branches = mGitHubClient.findRelatedBranches(repoName, owner);
            for(int x = 0; x < branches.size(); x++) {

                //Check only if commits have been made to master branch
                if (repo.isCommitNotificationOn() && branches.get(x).getName().equals("master")) {
                    boolean isCommitDifferent = this.mGitHubClient.isCommitDifferent(repoName, owner, branches.get(x).getName(), getApplicationContext());

                    if (isCommitDifferent) {
                        String message = "Commit to: " + repo + "/" + owner + "/" + branches.get(x).getName();

                        this.mHandler.post(new DisplayToast(this, message));
                        sendNotification(Integer.parseInt(i + "" + x), "New Commit", message);
                    }
                }

                if (repo.isMergeNotificationOn()) {
                    for (int y = x; y < branches.size(); y++) {
                        if (x != y) {
                            String fileConflict = this.mGitHubClient.compareBranch(repoName, owner, branches.get(x).getName(), branches.get(y).getName());
                            if (fileConflict.length() > 0) {
                                sendNotification(Integer.parseInt(i + "" + x + "" + y), "Possible conflict",  "In file(s): " + fileConflict);
                            }
                        }
                    }
                }

            }
        }
    }

    private void sendNotification(int id, String title, String text) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                                                                    .setSmallIcon(R.drawable.ic_drawer)
                                                                    .setContentTitle(title)
                                                                    .setContentText(text);

        //Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //Builds the notification and issues it.
        mNotifyMgr.notify(id, mBuilder.build());
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