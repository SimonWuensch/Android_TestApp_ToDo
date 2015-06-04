package sim.ssn.com.todo.listener;

import android.view.View;

import java.util.List;
import java.util.Map;

import sim.ssn.com.todo.resource.Todo;

/**
 * Created by Simon on 24.04.2015.
 */
public interface CustomListener {

    void addTodo(View view, Todo todo);
    void updateTodo(View view, Todo oldTodo, Todo newTodo);
    void deleteTodo(View view, Todo todo);
    Map<String, List<Todo>> getTodoMap();

    void handleCardClick(int position);
}
