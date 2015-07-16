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

public class AdapterKindList extends  RecyclerView.Adapter<ViewHolderKindList> {
    private List<Kind> kindList;
    private int layout;
    private View inflatedView;
    private CustomListener customListener;


    public AdapterKindList(int layout, Activity activity){
        this.customListener = (CustomListener) activity;
        this.kindList = customListener.getDataBase().getKindList();
        this.layout = layout;
    }

    @Override
    public ViewHolderKindList onCreateViewHolder(ViewGroup viewGroup, int position) {
        inflatedView = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        return new ViewHolderKindList(inflatedView);
    }

    @Override
    public void onBindViewHolder(ViewHolderKindList holder, int position) {
        final Kind kind  = kindList.get(position);
        final List<Todo> todoList = customListener.getDataBase().getTodoListByKind(kind.getId());
        holder.assignData(kind.getName(), todoList.size());

        inflatedView.setOnLongClickListener(customListener.handleEditKind(kind));
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
        return kindList.size();
    }
}
