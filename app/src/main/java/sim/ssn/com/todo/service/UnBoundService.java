package sim.ssn.com.todo.service;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UnBoundService extends Service {

    private static String TAG = UnBoundService.class.getSimpleName();

    public UnBoundService(){

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        Intent notificationReceiver = new Intent(getBaseContext(), NotificationReceiver.class);
        PendingIntent mAlarmSender = PendingIntent.getBroadcast(getBaseContext(), 0, notificationReceiver, 0);
        AlarmManager alarmManager = (AlarmManager) getBaseContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 3000, mAlarmSender);
        return Service.START_NOT_STICKY;
    }
}
