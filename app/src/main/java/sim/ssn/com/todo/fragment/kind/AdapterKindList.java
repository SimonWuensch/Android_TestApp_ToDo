package sim.ssn.com.todo.fragment.kind;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sim.ssn.com.todo.listener.CustomListener;
import sim.ssn.com.todo.resource.Kind;
import sim.ssn.com.todo.resource.Todo;

/**
 * Created by Simon on 04.06.2015.
 */
public class AdapterKindList extends  RecyclerView.Adapter<ViewHolderKindList> {
    //private Map<String, List<Todo>> todoMap;
    private List<Kind> kindlist;
    private int layout;
    private Activity activity;
    private View inflatedView;
    private CustomListener customListener;


    public AdapterKindList(int layout, Activity activity){
        this.customListener = (CustomListener) activity;
        this.kindlist = customListener.getDataBase().getKindList();
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
        final Kind kind  = kindlist.get(position);
        final List<Todo> todoList = customListener.getDataBase().getTodoListByKind(kind.getId());
        holder.assignData(kind.getName(), todoList.size());
        final int pos = position;

        inflatedView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                customListener.handleEditKind(kind);
                return false;
            }
        });
        inflatedView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent ev) {
                if(ev.getAction() == MotionEvent.ACTION_UP)
                    if(ev.getEventTime() - ev.getDownTime() < 500){
                        customListener.handleCardClick(kind.getId());
                    }
                return false;
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return kindlist.size();
    }
}
