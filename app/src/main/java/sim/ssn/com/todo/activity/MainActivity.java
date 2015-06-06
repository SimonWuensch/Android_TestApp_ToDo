package sim.ssn.com.todo.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import sim.ssn.com.todo.R;
import sim.ssn.com.todo.data.MyDataBaseSQLite;
import sim.ssn.com.todo.fragment.kind.KindListFragment;
import sim.ssn.com.todo.fragment.list.TodoListFragment;
import sim.ssn.com.todo.listener.CustomListener;
import sim.ssn.com.todo.resource.Kind;
import sim.ssn.com.todo.resource.Todo;
import sim.ssn.com.todo.ui.DialogManager;


public class MainActivity extends Activity implements CustomListener{

    public static String FRAGMENT_ORDERLIST = "orderlistfragment";
    public static String FRAGMENT_KINDLIST = "kindlistfragment";

    private TodoListFragment todoListFragment;
    private MyDataBaseSQLite dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBase = new MyDataBaseSQLite(this);
        showListTodoFragment(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showListTodoFragment(boolean showNew){
        if(showNew){
            getFragmentManager().beginTransaction().replace(R.id.First_flFragment_Container, new TodoListFragment(), FRAGMENT_ORDERLIST).commit();
            return;
        }
        getFragmentManager().beginTransaction().replace(R.id.First_flFragment_Container, todoListFragment, FRAGMENT_ORDERLIST).commit();
    }

    public String getActiveFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        if(fragmentManager.getBackStackEntryCount() == 0) {
            return null;
        }
        String tag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        Log.d(MainActivity.class.getSimpleName(), "Fragmenttag: " + tag );
        return tag;
    }

    @Override
    public void addTodo(View view, Todo todo) {
        dataBase.addTodo(todo);
    }

    @Override
    public void updateTodo(View view, Todo oldTodo, Todo newTodo) {
        dataBase.updateTodo(oldTodo, newTodo);
    }

    @Override
    public void deleteTodo(View view, Todo todo) {
        dataBase.deleteTodo(todo);
    }

    @Override
     public void handleCardClick(Kind kind) {
        Log.d(MainActivity.class.getSimpleName(), "Kind Clicked: " + kind);
        FragmentTransaction fragmentManager =  getFragmentManager().beginTransaction();
        fragmentManager.replace(R.id.First_flFragment_Container, KindListFragment.newInstance(kind), FRAGMENT_KINDLIST);
        fragmentManager.addToBackStack(FRAGMENT_ORDERLIST);
        fragmentManager.commit();
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
      public void handleEditKind(Kind kind){
        DialogManager.showEditKindDialog(this, kind);
    }

    @Override
    public View.OnClickListener handleAddTodo(){
        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Add Todo Button clicked", Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public View.OnClickListener handleAddKind(){
        final Activity activity = this;
        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Add Kind Button clicked", Toast.LENGTH_SHORT).show();
                DialogManager.showAddKindDialog(activity);
            }
        };
    }


}
