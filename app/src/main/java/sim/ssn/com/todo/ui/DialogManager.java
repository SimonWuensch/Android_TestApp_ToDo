package sim.ssn.com.todo.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sim.ssn.com.todo.R;
import sim.ssn.com.todo.activity.MainActivity;
import sim.ssn.com.todo.data.MyDataBaseSQLite;
import sim.ssn.com.todo.resource.Kind;
import sim.ssn.com.todo.resource.Todo;

/**
 * Created by Simon on 06.06.2015.
 */
public class DialogManager {

    private static String ADDBUTTON = "hinzufügen";
    private static String DELETEBUTTON = "löschen";
    private static String UPDATEBUTTON = "ändern";
    private static String CANCELBUTTON = "abbrechen";

    public static void showKindDialog(final Activity activity){
        showKindDialog(activity, null);
    }

    public static void showKindDialog(final Activity activity, final Kind kind){
        final MyDataBaseSQLite database = ((MainActivity)activity).getDataBase();
        View promptsView = LayoutInflater.from(activity).inflate(R.layout.dialog_add_update_delete_kind, null);
        final EditText etKindName = (EditText) promptsView.findViewById(R.id.dialog_kind_etKindName);
        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setView(promptsView)
                .setCancelable(false)
                .setPositiveButton(ADDBUTTON,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Button positiveButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                                if (positiveButton.getText().toString().equals(DELETEBUTTON)) {
                                    database.deleteKind(kind);
                                } else if (positiveButton.getText().toString().equals(UPDATEBUTTON)) {
                                    database.updateKind(kind, etKindName.getText().toString());
                                } else if (positiveButton.getText().toString().equals(ADDBUTTON)) {
                                    if (etKindName.getText().toString().length() > 0) {
                                        database.addKind(etKindName.getText().toString());
                                    } else {
                                        Toast.makeText(activity, "Der Listenname darf nicht leer sein!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                ((MainActivity) activity).showKindListFragment(true);
                            }
                        })
                .setNegativeButton(CANCELBUTTON,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button positiveButton = ((AlertDialog) dialog)
                        .getButton(AlertDialog.BUTTON_POSITIVE);

                if(kind == null){
                    positiveButton.setText(ADDBUTTON);
                }else {
                    positiveButton.setText(DELETEBUTTON);
                    etKindName.setText(kind.getName());
                    etKindName.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            Button positiveButton = ((AlertDialog) dialog)
                                    .getButton(AlertDialog.BUTTON_POSITIVE);
                            String kindName = etKindName.getText().toString();
                            if (kind.getName().equals(kindName)) {
                                positiveButton.setText(DELETEBUTTON);
                            } else {
                                positiveButton.setText(UPDATEBUTTON);
                            }

                            if(kindName.length() <= 0){
                                positiveButton.setEnabled(false);
                            }else{
                                positiveButton.setEnabled(true);
                            }
                        }
                    });
                }
            }
        });
        alertDialog.show();
    }

    public static void showTodoDialog(final Activity activity, final Todo todo){
        final DialogTodoHandler todoViewHandler = new DialogTodoHandler(activity, todo);
        View promptView = todoViewHandler.getRootView();
        final EditText etDescription = (EditText) promptView.findViewById(R.id.dialog_todo_etDescription);
        final MyDataBaseSQLite database = ((MainActivity)activity).getDataBase();

        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setView(promptView)
                .setCancelable(false)
                .setPositiveButton(ADDBUTTON,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Button positiveButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                                if (positiveButton.getText().toString().equals(DELETEBUTTON)) {
                                    database.deleteTodo(todo);
                                } else if (positiveButton.getText().toString().equals(UPDATEBUTTON)) {
                                    database.updateTodo(todoViewHandler.getTodo());
                                }else if (positiveButton.getText().toString().equals(ADDBUTTON)) {
                                    if(todoViewHandler.getTodo().getDescription().length() > 0)
                                        database.addTodo(todoViewHandler.getTodo());
                                }
                                ((MainActivity) activity).handleCardClick(todo.getKindID());
                            }
                        })
                .setNegativeButton(CANCELBUTTON,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button positiveButton = ((AlertDialog) dialog)
                        .getButton(AlertDialog.BUTTON_POSITIVE);

                if(todo.getId() <= 0){
                    positiveButton.setText(ADDBUTTON);
                }else {
                    positiveButton.setText(DELETEBUTTON);
                    etDescription.setText(todo.getDescription());
                    etDescription.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            Button positiveButton = ((AlertDialog) dialog)
                                    .getButton(AlertDialog.BUTTON_POSITIVE);
                            String description = etDescription.getText().toString();
                            if (todo.getDescription().equals(description)) {
                                positiveButton.setText(DELETEBUTTON);
                            } else {
                                positiveButton.setText(UPDATEBUTTON);
                            }

                            if(description.length() <= 0){
                                positiveButton.setEnabled(false);
                            }else{
                                positiveButton.setEnabled(true);
                            }
                        }
                    });
                }
            }
        });

        alertDialog.show();
    }
}
