package interview.com.commentssdk;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class Client {
    private static final int RC_COMMENT_BROADCAST = 12345;
    private static final int ALARM_PERIOD = 60000;
    private Context context;

    public Client(Context context) {
        this.context = context;
    }

    public void start() {
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(context, RC_COMMENT_BROADCAST, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + ALARM_PERIOD, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + ALARM_PERIOD, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + ALARM_PERIOD, pendingIntent);
        }
    }

}
