package com.example.user.sleepwell;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by user on 2018/3/30.
 */

public class SleepReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "收到了，一個小時後去睡覺", Toast.LENGTH_LONG).show();
    }
}
