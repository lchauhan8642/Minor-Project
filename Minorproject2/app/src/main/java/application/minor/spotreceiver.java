package application.minor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class spotreceiver extends BroadcastReceiver {
    public spotreceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, spotalarm.class);
        context.startService(i);

    }
}
