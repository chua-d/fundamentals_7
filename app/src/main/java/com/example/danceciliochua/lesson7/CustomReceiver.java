package com.example.danceciliochua.lesson7;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class CustomReceiver extends BroadcastReceiver {

    private static final String ACTION_CUSTOM_BROADCAST = "com.example.danceciliochua.lesson7.ACTION_CUSTOM_BROADCAST";

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();
        String toast_message = null;
        Log.d("TEST", "123123123123");

        switch(intentAction) {
            case Intent.ACTION_POWER_CONNECTED:
                toast_message = "Power connected!";
                break;

            case Intent.ACTION_POWER_DISCONNECTED:
                toast_message = "Power disconnected!";
                break;

            case ACTION_CUSTOM_BROADCAST:
                toast_message = "Custom Broadcast Received";
                break;

        }
        Toast.makeText(context, toast_message, Toast.LENGTH_SHORT).show();
    }
}
