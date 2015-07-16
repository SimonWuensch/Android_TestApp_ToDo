package sim.ssn.com.todo.dialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import sim.ssn.com.todo.R;
import sim.ssn.com.todo.resource.Todo;

public class DialogTodoHandler {

    private View rootView;
    private Todo todo;

    private EditText etDescription;
    private ImageView ivStar;

    public DialogTodoHandler(Activity activity, Todo todo) {
        this.todo = todo;
        this.rootView = LayoutInflater.from(activity).inflate(R.layout.dialog_add_update_delete_todo, null);
        initElements();
    }

    private void initElements(){
        CheckBox cbFinished = (CheckBox) rootView.findViewById(R.id.dialog_todo_cbFinished);
        etDescription = (EditText) rootView.findViewById(R.id.dialog_todo_etDescription);
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
