package sim.ssn.com.todo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;
import java.util.Map;

import sim.ssn.com.todo.R;
import sim.ssn.com.todo.data.MyDataBaseSQLite;
import sim.ssn.com.todo.fragment.list.TodoListFragment;
import sim.ssn.com.todo.listener.CustomListener;
import sim.ssn.com.todo.resource.Todo;


public class MainActivity extends Activity implements CustomListener{



    public static String FRAGMENT_ORDERLIST = "orderlistfragment";

    private TodoListFragment todoListFragment;
    private MyDataBaseSQLite dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showListProjectFragment(true);
        dataBase = new MyDataBaseSQLite(this);
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

    private void showListProjectFragment(boolean showNew){
        if(showNew){
            getFragmentManager().beginTransaction().replace(R.id.First_flFragment_Container, new TodoListFragment(), FRAGMENT_ORDERLIST).commit();
            return;
        }
        getFragmentManager().beginTransaction().replace(R.id.First_flFragment_Container, todoListFragment, FRAGMENT_ORDERLIST).commit();
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
    public void handleCardClick(int position) {
        Log.d(MainActivity.class.getSimpleName(), "Card Clicked: " + position);
    }

    @Override
    public Map<String, List<Todo>> getTodoMap(){
       return dataBase.getTodoMap();
    }
}
