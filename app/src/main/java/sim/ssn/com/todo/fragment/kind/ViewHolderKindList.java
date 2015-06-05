package sim.ssn.com.todo.fragment.kind;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sim.ssn.com.todo.R;
import sim.ssn.com.todo.resource.Todo;

/**
 * Created by Simon on 05.06.2015.
 */
public class ViewHolderKindList extends RecyclerView.ViewHolder {

    private Activity activity;
    private View itemView;

    private TextView tvDescription;
    private ImageView ivStar;


    public ViewHolderKindList(Activity activity, View itemView) {
        super(itemView);
        this.activity = activity;
        this.itemView = itemView;

        tvDescription = (TextView) itemView.findViewById(R.id.CardView_list_kind_tvDescription);
        ivStar = (ImageView) itemView.findViewById(R.id.CardView_list_kind_ivStar);
    }

    public void assignData(Todo todo) {
        tvDescription.setText(todo.getDescription());
        if(todo.isImportant())
            ivStar.setImageResource(R.drawable.icon_star_filled);
        else
            ivStar.setImageResource(R.drawable.icon_star_not_filled);
    }
}