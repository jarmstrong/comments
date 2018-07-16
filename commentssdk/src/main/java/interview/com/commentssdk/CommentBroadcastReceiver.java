package interview.com.commentssdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CommentBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent i) {
        // Re-arm alarm, this is so that we can use the AlarmManager through doze-mode.
        CommentClient client = new CommentClient(context);
        client.start();

        Intent serviceIntent = new Intent(context, CommentIntentService.class);
        context.startService(serviceIntent);
    }
}
