package application.minor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class receiver  extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("ABC","receivedspot");

            Intent i = new Intent(context, alarm.class);
            context.startService(i);

    }

}