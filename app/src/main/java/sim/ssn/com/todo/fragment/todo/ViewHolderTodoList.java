package sim.ssn.com.todo.fragment.todo;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import sim.ssn.com.todo.R;
import sim.ssn.com.todo.listener.CustomListener;
import sim.ssn.com.todo.resource.Todo;

public class ViewHolderTodoList extends RecyclerView.ViewHolder {

    private TextView etDescription;
    private CheckBox cbFinished;
    private ImageView ivStar;

    private CustomListener customListener;


    public ViewHolderTodoList(Activity activity, View itemView) {
        super(itemView);
        this.customListener = (CustomListener)activity;

        etDescription = (TextView) itemView.findViewById(R.id.CardView_todo_etDescription);
        cbFinished = (CheckBox) itemView.findViewById(R.id.CardView_todo_cbFinished);
        ivStar = (ImageView) itemView.findViewById(R.id.CardView_todo_ivStar);
    }

    public void assignData(final Todo todo) {
        etDescription.setText(todo.getDescription());

        cbFinished.setChecked(todo.isFinished());
        cbFinished.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isFinished) {
                todo.setFinished(isFinished);
                customListener.getDataBase().updateTodo(todo);
            }
        });

        ivStar.setImageResource(todo.isImportant() ? R.drawable.icon_star_filled : R.drawable.icon_star_not_filled);
        ivStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivStar.setImageResource(todo.toggleImportant() ? R.drawable.icon_star_filled : R.drawable.icon_star_not_filled);
                customListener.getDataBase().updateTodo(todo);
            }
        });

    }
}