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

    MyDataBaseSQLite getDataBase();

    void handleCardClick(long kindId);
    void handleCardClick(Todo todo);
    void handleEditKind(Kind kind);
    void handleEditTodo(Todo todo);

    View.OnClickListener handleAddTodo(final long kindId);
    View.OnClickListener handleAddKind();

}
