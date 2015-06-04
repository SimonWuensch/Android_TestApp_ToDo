package sim.ssn.com.todo.fragment.list;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;
import java.util.Map;

import sim.ssn.com.todo.R;
import sim.ssn.com.todo.activity.MainActivity;
import sim.ssn.com.todo.listener.CustomListener;
import sim.ssn.com.todo.resource.Todo;

/**
 * Created by Simon on 04.06.2015.
 */
public class AdapterTodoList extends  RecyclerView.Adapter<ViewHolderTodoList> {
    private final Map<String, List<Todo>> todoMap;
    private final int layout;
    private final Activity activity;
    private CustomListener customListener;

    private View inflatedView;

    public AdapterTodoList(int layout, Activity activity){
        this.layout = layout;
        this.activity = activity;
        this.customListener = (MainActivity)activity;
        this.todoMap = customListener.getTodoMap();
    }

    @Override
    public ViewHolderTodoList onCreateViewHolder(ViewGroup viewGroup, int position) {
        inflatedView = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        ViewHolderTodoList viewHolder = new ViewHolderTodoList(activity, inflatedView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderTodoList holder, final int position) {
        String kind = (String) todoMap.keySet().toArray()[position];
        holder.assignData(kind, todoMap.get(kind).size());

        inflatedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customListener.handleCardClick(position);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return todoMap.keySet().size();
    }
}
