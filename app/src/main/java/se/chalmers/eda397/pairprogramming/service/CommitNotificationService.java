package se.chalmers.eda397.pairprogramming.service;

import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.os.IBinder;

public class CommitNotificationService extends Service {

    CommitNotificationReceiver mCommitReceiver = new CommitNotificationReceiver();

    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        mCommitReceiver.setNotification(CommitNotificationService.this);
        return START_STICKY;
    }

    public void onStart(Context context,Intent intent, int startId)
    {
        mCommitReceiver.setNotification(context);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

}
