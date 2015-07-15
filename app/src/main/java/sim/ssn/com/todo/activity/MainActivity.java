package sim.ssn.com.todo.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.facebook.login.LoginManager;

import sim.ssn.com.todo.R;
import sim.ssn.com.todo.data.CustomSharedPreferences;
import sim.ssn.com.todo.data.MyDataBaseSQLite;
import sim.ssn.com.todo.fragment.todo.TodoListFragment;
import sim.ssn.com.todo.fragment.kind.KindListFragment;
import sim.ssn.com.todo.listener.CustomListener;
import sim.ssn.com.todo.resource.Kind;
import sim.ssn.com.todo.resource.Todo;
import sim.ssn.com.todo.resource.User;
import sim.ssn.com.todo.ui.DialogManager;

public class MainActivity extends ActionBarActivity implements CustomListener{

    public static String FRAGMENT_TODOLIST = "orderlistfragment";
    public static String FRAGMENT_KINDLIST = "kindlistfragment";

    private KindListFragment kindListFragment;
    private MyDataBaseSQLite dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showKindListFragment(false);
        initVariables();
    }

    private void initVariables(){
        dataBase = new MyDataBaseSQLite(this);

        TextView etLoginName = (TextView) findViewById(R.id.activity_main_tvLoginName);
        User user = CustomSharedPreferences.getUser(this);
        etLoginName.setText(user.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_activity_main_logout) {
            CustomSharedPreferences.removeUser(this);
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showKindListFragment(boolean withFlipIn){
        if(withFlipIn) {
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(R.animator.flip_in, R.animator.flip_out)
                    .replace(R.id.First_flFragment_Container, new KindListFragment(), FRAGMENT_KINDLIST)
                    .addToBackStack(null)
                    .commit();
        }
        else{
            getFragmentManager().beginTransaction()
                    .replace(R.id.First_flFragment_Container, new KindListFragment(), FRAGMENT_KINDLIST)
                    .addToBackStack(null)
                    .commit();
        }
    }

    public void showTodoListFragment(long kindId){
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.flip_in, R.animator.flip_out)
                .replace(R.id.First_flFragment_Container, TodoListFragment.newInstance(kindId), FRAGMENT_TODOLIST)
                .addToBackStack(FRAGMENT_TODOLIST)
                .commit();
    }

    @Override
    public void onBackPressed() {
        String fragmentTag = getActiveFragment();
        if(fragmentTag == null){
            super.onBackPressed();
        }else if(fragmentTag.equals(FRAGMENT_TODOLIST)){
            showKindListFragment(true);
        }
    }

    private String getActiveFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        if(fragmentManager.getBackStackEntryCount() == 0) {
            return null;
        }
        String tag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        Log.d(MainActivity.class.getSimpleName(), "Fragmenttag: " + tag );
        return tag;
    }

    @Override
     public void handleCardClick(long kindId) {
        Log.d(MainActivity.class.getSimpleName(), "Kind Clicked: " + kindId);
        showTodoListFragment(kindId);
    }

    @Override
    public void handleCardClick(Todo todo) {
        Log.d(MainActivity.class.getSimpleName(), "Todo clicked: " + todo.toJson());
    }

    @Override
    public MyDataBaseSQLite getDataBase(){
       return dataBase;
    }

    @Override
    public void handleEditTodo(Todo todo){
        DialogManager.showTodoDialog(this,todo);
    }

    @Override
    public View.OnClickListener handleAddTodo(final long kindId){
        final Activity activity = this;
        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogManager.showTodoDialog(activity,new Todo(kindId, ""));
            }
        };
    }

    @Override
    public void handleEditKind(Kind kind){
        DialogManager.showKindDialog(this, kind);
    }

    @Override
    public View.OnClickListener handleAddKind(){
        final Activity activity = this;
        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogManager.showKindDialog(activity);
            }
        };
    }


}
