package sim.ssn.com.todo.fragment.list;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

import sim.ssn.com.todo.listener.CustomListener;
import sim.ssn.com.todo.resource.Todo;

/**
 * Created by Simon on 04.06.2015.
 */
public class AdapterTodoList extends  RecyclerView.Adapter<ViewHolderTodoList> {
    private Map<String, List<Todo>> todoMap;
    private int layout;
    private Activity activity;
    private View inflatedView;
    private CustomListener customListener;


    public AdapterTodoList(int layout, Activity activity){
        this.customListener = (CustomListener) activity;
        this.todoMap = customListener.getDataBase().getTodoMap();
        this.layout = layout;
        this.activity = activity;
    }

    @Override
    public ViewHolderTodoList onCreateViewHolder(ViewGroup viewGroup, int position) {
        inflatedView = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        ViewHolderTodoList viewHolder = new ViewHolderTodoList(activity, inflatedView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderTodoList holder, int position) {
        final String kind  = (String) todoMap.keySet().toArray()[position];
        holder.assignData(kind, todoMap.get(kind).size());
        final int pos = position;
        inflatedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customListener.handleCardClick(kind);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return todoMap.size();
    }
}
