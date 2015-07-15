package sim.ssn.com.todo.fragment.kind;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import sim.ssn.com.todo.R;

/**
 * Created by Simon on 04.06.2015.
 */
public class ViewHolderKindList extends RecyclerView.ViewHolder {

    private Activity activity;
    private View itemView;

    private TextView tvListName, tvCount;

    public ViewHolderKindList(Activity activity, View itemView) {
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
