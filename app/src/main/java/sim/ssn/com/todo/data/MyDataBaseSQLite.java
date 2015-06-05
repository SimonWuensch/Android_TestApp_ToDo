package sim.ssn.com.todo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sim.ssn.com.todo.resource.Todo;

/**
 * Created by Simon on 22.04.2015.
 */
public class MyDataBaseSQLite extends SQLiteOpenHelper {

    public List<String> kinds = new ArrayList<String>(){
        {
            add("Wichtig");
            add("Arbeit");
            add("Besorgungen");
            add("Studium");
        }
    };

    private static final String TAG = MyDataBaseSQLite.class.getSimpleName();

    private static final String DATABASE_NAME = "Todo.db";
    private static final int DATABASE_VERSION = 1;

    private static final String _ID = "id";
    private static final String _KIND = "kind";
    private static final String _TODO_JSON = "projectjson";
    private static final String TABLE_TODO = "project";


    private static final String TABLE_TODO_CREATE =
            "CREATE TABLE "
                + TABLE_TODO + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                      + _KIND + " VARCHAR(15), "
                                      + _TODO_JSON + " VARCHAR(1000));";

    private static final String TABLE_TODO_DROP =
            "DROP TABLE IF EXISTS " + TABLE_TODO;



    public MyDataBaseSQLite(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_TODO_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Database upgrade from version "
                + oldVersion + " to "
                + newVersion);
        db.execSQL(TABLE_TODO_DROP);
        onCreate(db);
    }

    public Cursor query(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_TODO, null, null, null, null, null, _ID + " DESC");
        return db.query(TABLE_TODO, null, null, null, null, null, _ID + " DESC");
    }

    public List<Todo> getTodoListByKind(String kind){
        Map<String, List<Todo>> todoMap = getTodoMap();
        if(kind != null){
            if(todoMap.containsKey(kind)){
                return todoMap.get(kind);
            }
        }else{
            List<Todo> todoList = new ArrayList<Todo>();
            for(String key : todoMap.keySet()){
                todoList.addAll(todoMap.get(key));
            }
            return todoList;
        }
        return new ArrayList<>();
    }

    public List<Todo> getTodoList(){
       return getTodoListByKind(null);
    }

    public Map<String, List<Todo>> getTodoMap(){
        Map<String, List<Todo>> todoMap = new HashMap<>();

        for(String kind : kinds){
            todoMap.put(kind, new ArrayList<Todo>(){
                {
                    add(new Todo());
                }
            });
        }

        Cursor cursor = query();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    long id = Long.parseLong(cursor.getString(cursor.getColumnIndex(_ID)));
                    String jsonString = cursor.getString(cursor.getColumnIndex(_TODO_JSON));
                    final Todo todo = Todo.JsonToProject(jsonString);
                    todo.setId(id);

                    List<Todo> todos = todoMap.get(todo.getKind());
                    todos = todos != null ? todos : new ArrayList<Todo>();
                    todos.add(todo);
                    todoMap.put(todo.getKind(), todos);
                    cursor.moveToNext();
                }
            }
        }
        cursor.close();
        return todoMap;
    }



    public void addTodo(final Todo todo){
        long rowId = -1;
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(_KIND, todo.getKind());
            values.put(_TODO_JSON, todo.toJson());
            rowId = db.insert(TABLE_TODO, null, values);
        }catch(SQLiteException e){
            Log.e(TAG, "ADD ERROR: " + todo.toJson(), e);
        }finally{
            Log.d(TAG, "ADD: " + todo.toJson());
        }
    }

    public void deleteTodo(Todo todo){
        try{
            SQLiteDatabase db = getWritableDatabase();
            db.delete(TABLE_TODO, _ID + " = ?", new String[] {
                    Long.toString(todo.getId())
            });
        }catch(SQLiteException e){
            Log.e(TAG, "DELETE ERROR: ", e);
        }finally{
            Log.d(TAG, "DELETE: " + todo.toJson());
        }
    }

    public void updateTodo(Todo oldTodo, Todo newTodo){
        try{
           deleteTodo(oldTodo);
           addTodo(newTodo);
        }catch(SQLiteException e){
            Log.e(TAG, "UPDATE ERROR ", e);
        }finally{
            Log.d(TAG, "UPDATE from: \t" + oldTodo.toJson() + "\nto: \t" + newTodo.toJson());
        }
    }
}
