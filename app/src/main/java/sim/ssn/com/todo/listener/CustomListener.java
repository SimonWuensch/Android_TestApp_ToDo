package sim.ssn.com.todo.listener;

import android.view.View;

import sim.ssn.com.todo.data.MyDataBaseSQLite;
import sim.ssn.com.todo.resource.Kind;
import sim.ssn.com.todo.resource.Todo;

public interface CustomListener {

    MyDataBaseSQLite getDataBase();

    void handleCardClick(long kindId);
    void handleCardClick(Todo todo);

    View.OnLongClickListener handleEditKind(Kind kind);
    View.OnLongClickListener handleEditTodo(Todo todo);

    View.OnClickListener handleAddTodo(final long kindId);
    View.OnClickListener handleAddKind();

}
