package sim.ssn.com.todo.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import sim.ssn.com.todo.activity.LoginActivity;
import sim.ssn.com.todo.resource.User;

/**
 * Created by Simon on 14.07.2015.
 */
public class CustomSharedPreferences {

    private static String SHAREDPREFSKEY = "sharedPrefferencesKey";
    public static String EXTRALOGINNAME = "LoginName";
    public static String EXTRALOGINPASSWORD = "LoginPassword";

    public static void addUser(Activity activity, User user){
        SharedPreferences.Editor editor = activity.getSharedPreferences(SHAREDPREFSKEY, Context.MODE_PRIVATE).edit();
        editor.putString(EXTRALOGINNAME, user.getUserName());
        editor.putString(EXTRALOGINPASSWORD, user.getPassword());
        editor.commit();
    }

    public static User getUser(Activity activity){
        SharedPreferences pref = activity.getSharedPreferences(SHAREDPREFSKEY, Context.MODE_PRIVATE);
        final String username = pref.getString(EXTRALOGINNAME, User.DEFAULTUSERNAME).toString();
        final String password = pref.getString(EXTRALOGINPASSWORD, User.DEFAULTPASSWORD).toString();
        return new User(username, password);
    }

    public static void removeUser(Activity activity){
        SharedPreferences preferences = activity.getSharedPreferences(SHAREDPREFSKEY, 0);
        preferences.edit().remove(EXTRALOGINNAME).commit();
        preferences.edit().remove(EXTRALOGINPASSWORD).commit();
    }
}

