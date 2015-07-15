package sim.ssn.com.todo.notification;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import sim.ssn.com.todo.R;
import sim.ssn.com.todo.activity.MainActivity;
import sim.ssn.com.todo.resource.Kind;
import sim.ssn.com.todo.resource.Todo;

public class NotificationHandler {

    private static String TODOADDED = "Todo wurde hinzugefügt.";
    private static String TODOREMOVED = "Todo wurde entfernt.";
    private static String TODOEDITED = "Todo wurde angepasst.";

    private Activity activity;
    private int count;

    public NotificationHandler(Activity activity){
        this.activity = activity;
    }

    public void throwAddTodoNotification(Todo todo, String info, String text){
        throwNotification(todo, TODOADDED, info, text);
    }

    public void throwRemoveTodoNotification(Todo todo, String info, String text){
        throwNotification(todo, TODOREMOVED, info, text);
    }

    public void throwEditTodoNotification(Todo todo, String info, String text){
        throwNotification(todo, TODOEDITED, info, text);
    }

    private void throwNotification(Todo todo, String title, String info, String text) {
        Intent myIntent = new Intent(activity, MainActivity.class);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(activity, 0, myIntent, 0);

        long[] vibrate = new long[]{1000, 500, 1000};
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(activity)
                .setContentTitle(title)
                .setSmallIcon(todo.isFinished() ? R.drawable.icon_star_filled : R.drawable.icon_star_not_filled)
                .setWhen(System.currentTimeMillis())
                .setContentInfo(info)
                .setContentText(text)
                .setVibrate(vibrate)
                .setContentIntent(viewPendingIntent)
                .setLights(Color.RED, 500, 500);
        count++;
        NotificationManagerCompat.from(activity).notify(count, mBuilder.build());
    }
}
