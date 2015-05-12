package se.chalmers.eda397.pairprogramming;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CommitNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent inService = new Intent(context, CommitNotificationService.class);
        context.startService(inService);
    }

}
