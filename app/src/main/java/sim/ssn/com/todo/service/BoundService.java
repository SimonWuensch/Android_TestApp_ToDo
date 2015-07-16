package sim.ssn.com.todo.service;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BoundService extends Service {

    private static String TAG  = BoundService.class.getSimpleName();

    public class LocalBinder extends Binder {
        public BoundService getService() {
            return BoundService.this;
        }
    }

    private Activity activity;


    public BoundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return new LocalBinder();
    }

    public double foo(){
        Log.d(TAG, "foo");
        return Math.random();
    }
}
