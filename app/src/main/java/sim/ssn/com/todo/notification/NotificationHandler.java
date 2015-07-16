package sim.ssn.com.todo.notification;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import sim.ssn.com.todo.R;
import sim.ssn.com.todo.activity.MainActivity;
import sim.ssn.com.todo.resource.Todo;

public class NotificationHandler {

    private static String TODOADDED = "Todo wurde hinzugefügt.";
    private static String TODOREMOVED = "Todo wurde entfernt.";
    private static String TODOEDITED = "Todo wurde angepasst.";

    private Context context;
    private int count = 0;

    public NotificationHandler(Context context){
        this.context = context;
    }

    public void throwAddTodoNotification(Todo todo, String info, String text){
        throwNotification(todo.isImportant(), TODOADDED, info, text);
    }

    public void throwDeleteTodoNotification(Todo todo, String info, String text){
        throwNotification(todo.isImportant(), TODOREMOVED, info, text);
    }

    public void throwUpdateTodoNotification(Todo todo, String info, String text){
        throwNotification(todo.isImportant(), TODOEDITED, info, text);
    }

    public void throwNotification(boolean isImportant, String title, String info, String text) {
        Intent myIntent = new Intent(context, MainActivity.class);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(context, 0, myIntent, 0);

        long[] vibrate = new long[]{1000, 500, 1000};
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setSmallIcon(isImportant ? R.drawable.icon_star_filled : R.drawable.icon_star_not_filled)
                .setWhen(System.currentTimeMillis())
                .setContentInfo(info)
                .setContentText(text)
                .setVibrate(vibrate)
                .setContentIntent(viewPendingIntent)
                .setLights(Color.RED, 500, 500);
        count++;
        NotificationManagerCompat.from(context).notify(count, mBuilder.build());
    }
}
