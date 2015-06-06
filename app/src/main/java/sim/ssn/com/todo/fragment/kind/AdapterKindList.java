package sim.ssn.com.todo.fragment.kind;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

import sim.ssn.com.todo.fragment.list.ViewHolderTodoList;
import sim.ssn.com.todo.listener.CustomListener;
import sim.ssn.com.todo.resource.Kind;
import sim.ssn.com.todo.resource.Todo;

/**
 * Created by Simon on 05.06.2015.
 */
public class AdapterKindList extends RecyclerView.Adapter<ViewHolderKindList>{
    private List<Todo> todoList;
    private int layout;
    private Activity activity;
    private View inflatedView;
    private CustomListener customListener;

    public AdapterKindList(int layout, Activity activity, List<Todo> todoList){
        this.customListener = (CustomListener) activity;
        this.todoList = todoList;
        this.layout = layout;
        this.activity = activity;
    }

    @Override
    public ViewHolderKindList onCreateViewHolder(ViewGroup viewGroup, int position) {
        inflatedView = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        ViewHolderKindList viewHolder = new ViewHolderKindList(activity, inflatedView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderKindList holder, int position) {
        final Todo todo = todoList.get(position);
        holder.assignData(todo);
        inflatedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customListener.handleCardClick(todo);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }
}
