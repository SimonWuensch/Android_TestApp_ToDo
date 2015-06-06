package sim.ssn.com.todo.fragment.list;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import sim.ssn.com.todo.R;
import sim.ssn.com.todo.resource.Todo;

/**
 * Created by Simon on 04.06.2015.
 */
public class ViewHolderTodoList extends RecyclerView.ViewHolder {

    private Activity activity;
    private View itemView;

    private TextView tvListName, tvCount;

    public ViewHolderTodoList(Activity activity, View itemView) {
        super(itemView);
        this.activity = activity;
        this.itemView = itemView;
        tvListName = (TextView) itemView.findViewById(R.id.CardView_list_todo_tvListName);
        tvCount = (TextView) itemView.findViewById(R.id.CardView_list_todo_tvCount);
    }

    public void assignData(String kind, int size){
        tvListName.setText(kind);
        tvCount.setText(Integer.toString(size));
    }
}
