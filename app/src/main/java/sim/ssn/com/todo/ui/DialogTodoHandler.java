package sim.ssn.com.todo.ui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import sim.ssn.com.todo.R;
import sim.ssn.com.todo.resource.Todo;

/**
 * Created by Simon on 07.06.2015.
 */
public class DialogTodoHandler {

    private View rootView;
    private Activity activity;
    private int layout;
    private Todo todo;

    private EditText etDescription;
    private CheckBox cbFinished;
    private ImageView ivStar;

    public DialogTodoHandler(Activity activity, Todo todo) {
        this.activity = activity;
        this.layout = R.layout.dialog_add_update_delete_todo;
        this.todo = todo;
        this.rootView = LayoutInflater.from(activity).inflate(layout, null);
        initElements();
    }

    private void initElements(){
        etDescription = (EditText) rootView.findViewById(R.id.dialog_todo_etDescription);
        cbFinished = (CheckBox) rootView.findViewById(R.id.dialog_todo_cbFinished);
        ivStar = (ImageView) rootView.findViewById(R.id.dialog_todo_ivStar);

        etDescription.setText(todo.getDescription());
        cbFinished.setChecked(todo.isFinished());
        ivStar.setImageResource(todo.isImportant() ? R.drawable.icon_star_filled : R.drawable.icon_star_not_filled);
        ivStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivStar.setImageResource(todo.toggleImportant() ? R.drawable.icon_star_filled : R.drawable.icon_star_not_filled);
            }
        });
    }

    public Todo getTodo(){
        todo.setDescription(etDescription.getText().toString());
        return todo;
    }

    public View getRootView(){
        return rootView;
    }
}
