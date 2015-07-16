package sim.ssn.com.todo.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import sim.ssn.com.todo.notification.NotificationHandler;

/**
 * Created by Simon on 16.07.2015.
 */
public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHandler notificationHandler = new NotificationHandler(context);
        notificationHandler.throwNotification(true, "BroadcastReceiver Notification", "info", "text");
    }
}
