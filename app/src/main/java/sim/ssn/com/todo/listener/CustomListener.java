package sim.ssn.com.todo.listener;

import android.view.View;
import android.widget.ImageView;

import java.util.List;
import java.util.Map;

import sim.ssn.com.todo.data.MyDataBaseSQLite;
import sim.ssn.com.todo.resource.Kind;
import sim.ssn.com.todo.resource.Todo;

/**
 * Created by Simon on 24.04.2015.
 */
public interface CustomListener {

    void addTodo(View view, Todo todo);
    void updateTodo(View view, Todo oldTodo, Todo newTodo);
    void deleteTodo(View view, Todo todo);
    MyDataBaseSQLite getDataBase();

    void handleCardClick(Kind kind);
    void handleCardClick(Todo todo);
    void handleEditKind(Kind kind);

    View.OnClickListener handleAddTodo();
    View.OnClickListener handleAddKind();

}
