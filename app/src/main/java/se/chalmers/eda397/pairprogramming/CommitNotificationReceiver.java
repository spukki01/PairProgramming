package se.chalmers.eda397.pairprogramming;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CommitNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
    }

}
