package interview.com.commentssdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class AlarmBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent i) {
        // Re-arm alarm, this is so that we can use the AlarmManager through doze-mode.
        Client client = new Client(context);
        client.start();

        Intent serviceIntent = new Intent(context, CommentIntentService.class);
        context.startService(serviceIntent);
    }
}
