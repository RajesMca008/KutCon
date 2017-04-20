package kutumblink.appants.com.kutumblink.fragments;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import kutumblink.appants.com.kutumblink.HomeActivity;

/**
 * Created by rrallabandi on 4/20/2017.
 */

public class   NotificationPublisher extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);

        Intent notificationIntent = new Intent(context,HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,notificationIntent, 0);

        notification.contentIntent=pendingIntent;
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(id, notification);

    }
}


