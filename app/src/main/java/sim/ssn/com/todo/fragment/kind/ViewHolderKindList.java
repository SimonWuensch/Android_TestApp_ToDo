package sim.ssn.com.todo.fragment.kind;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import sim.ssn.com.todo.R;

public class ViewHolderKindList extends RecyclerView.ViewHolder {


    private TextView tvListName, tvCount;

    public ViewHolderKindList(View itemView) {
        super(itemView);
        tvListName = (TextView) itemView.findViewById(R.id.CardView_list_todo_tvListName);
        tvCount = (TextView) itemView.findViewById(R.id.CardView_list_todo_tvCount);
    }

    public void assignData(String kind, int size){
        tvListName.setText(kind);
        tvCount.setText(Integer.toString(size));
    }
}
