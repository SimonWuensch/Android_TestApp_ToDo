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

import sim.ssn.com.todo.R;
import sim.ssn.com.todo.activity.MainActivity;
import sim.ssn.com.todo.data.MyDataBaseSQLite;
import sim.ssn.com.todo.resource.Kind;

/**
 * Created by Simon on 06.06.2015.
 */
public class DialogManager {

    public static void showAddKindDialog(final Activity activity){
        final MyDataBaseSQLite database = ((MainActivity)activity).getDataBase();
        View promptsView = LayoutInflater.from(activity).inflate(R.layout.dialog_add_kind, null);

        final EditText etKindName = (EditText) promptsView
                .findViewById(R.id.dialog_add_kind_etKindName);

        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setView(promptsView)
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                database.addKind(etKindName.getText().toString());
                                ((MainActivity)activity).showListTodoFragment(true);
                            }
                        })
                .setNegativeButton("abbrechen",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).create();
        alertDialog.show();
    }

    public static void showEditKindDialog(final Activity activity, final Kind kind){
        final MyDataBaseSQLite database = ((MainActivity)activity).getDataBase();
        View promptsView = LayoutInflater.from(activity).inflate(R.layout.dialog_add_kind, null);

        final EditText etKindName = (EditText) promptsView
                .findViewById(R.id.dialog_add_kind_etKindName);
        etKindName.setText(kind.getName());

        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setView(promptsView)
                .setCancelable(false)
                .setPositiveButton("löschen",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                Button positiveButton = ((AlertDialog) dialog)
                                        .getButton(AlertDialog.BUTTON_POSITIVE);

                                if(positiveButton.getText().toString().equals("löschen"))
                                    database.deleteKind(kind);
                                else if(positiveButton.getText().toString().equals("ändern"))
                                    database.updateKind(kind, new Kind(kind.getId(), etKindName.getText().toString()));
                                ((MainActivity)activity).showListTodoFragment(true);
                            }
                        })
                .setNegativeButton("abbrechen",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {

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
                        positiveButton.setText("ändern");
                    }
                });
            }
        });
        alertDialog.show();
    }
}
