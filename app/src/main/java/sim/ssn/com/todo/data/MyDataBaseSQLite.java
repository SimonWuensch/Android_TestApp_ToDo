package sim.ssn.com.todo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sim.ssn.com.todo.notification.NotificationHandler;
import sim.ssn.com.todo.resource.Kind;
import sim.ssn.com.todo.resource.Todo;

/**
 * Created by Simon on 22.04.2015.
 */
public class MyDataBaseSQLite extends SQLiteOpenHelper {

    private List<String> defaultKindList = new ArrayList<String>(){
        {
            add("Arbeit");
            add("Studium");
            add("Lernen");
            add("Besorgungen");
        }
    };

    private boolean updateKindList = true;
    private boolean updateTodoMap = true;

    private NotificationHandler notificationHandler;
    private Map<Long, List<Todo>> todoMap;
    private List<Kind> kindList;

    private static final String TAG = MyDataBaseSQLite.class.getSimpleName();

    private static final String DATABASE_NAME = "Todo.db";
    private static final int DATABASE_VERSION = 3;

    private static final String _ID = "id";
    private static final String _KIND = "kind";
    private static final String _TODO_JSON = "todojson";

    private static final String TABLE_TODO = "todo";
    private static final String TABLE_KIND = "kind";


    private static final String TABLE_TODO_CREATE =
            "CREATE TABLE "
                + TABLE_TODO + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                      + _KIND + " INTEGER, "
                                      + _TODO_JSON + " VARCHAR(1000), "
                                      + "FOREIGN KEY (" + _KIND + ") REFERENCES " + TABLE_KIND + " (" + _ID + "));";

    private static final String TABLE_KIND_CREATE =
            "CREATE TABLE "
                    + TABLE_KIND + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + _KIND + " VARCHAR(15));";

    private static final String TABLE_TODO_DROP =
            "DROP TABLE IF EXISTS " + TABLE_TODO;

    private static final String TABLE_KIND_DROP =
            "DROP TABLE IF EXISTS " + TABLE_KIND;



    public MyDataBaseSQLite(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        notificationHandler = new NotificationHandler(context);
        ifFirstStart();
    }

    private void ifFirstStart(){
        if(getKindList().size() == 0){
            for(String kind : defaultKindList){
                addKind(kind);
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_KIND_CREATE);
        db.execSQL(TABLE_TODO_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Database upgrade from version "
                + oldVersion + " to "
                + newVersion);
        db.execSQL(TABLE_TODO_DROP);
        db.execSQL(TABLE_KIND_DROP);
        onCreate(db);
    }

    public Cursor queryTableTodo(){
        SQLiteDatabase db = getWritableDatabase();
        return db.query(TABLE_TODO, null, null, null, null, null, _ID + " DESC");
    }

    public Cursor queryTableKind(){
        SQLiteDatabase db = getWritableDatabase();
        return db.query(TABLE_KIND, null, null, null, null, null, _ID + " DESC");
    }

    public List<Todo> getTodoListByKind(long kindID){
        Map<Long, List<Todo>> todoMap = getTodoMap();
        if(kindID != -1){
            if(todoMap.containsKey(kindID)){
                return todoMap.get(kindID);
            }
        }else{
            List<Todo> todoList = new ArrayList<Todo>();
            for(long key : todoMap.keySet()){
                todoList.addAll(todoMap.get(key));
            }
            return todoList;
        }
        return new ArrayList<>();
    }

    public List<Todo> getTodoList(){
        return getTodoListByKind(-1);
    }

    public Kind getKindByID(long id){
        for(Kind kind : kindList){
            if(kind.getId() == id){
                return kind;
            }
        }
        throw new NullPointerException("Kind List contains not a kind with this id [" + id + "].");
    }

    public List<Kind> getKindList(){
        if(updateKindList) {
            kindList = new ArrayList<Kind>();
            Cursor cursor = queryTableKind();
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        long id = Long.parseLong(cursor.getString(cursor.getColumnIndex(_ID)));
                        String kindName = cursor.getString(cursor.getColumnIndex(_KIND));
                        Kind kind = new Kind(id, kindName);
                        kindList.add(kind);
                        cursor.moveToNext();
                    }
                }
            }
            cursor.close();
            updateKindList = false;
            return kindList;
        }else{
            return kindList;
        }
    }

    public Map<Long, List<Todo>> getTodoMap(){
        if(updateTodoMap) {
            todoMap = new HashMap<Long, List<Todo>>();
            Cursor cursor = queryTableTodo();
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        long id = Long.parseLong(cursor.getString(cursor.getColumnIndex(_ID)));
                        String jsonString = cursor.getString(cursor.getColumnIndex(_TODO_JSON));
                        final Todo todo = Todo.JsonToTodo(jsonString);
                        todo.setId(id);

                        List<Todo> todos = todoMap.get(todo.getKindID());
                        todos = todos != null ? todos : new ArrayList<Todo>();
                        todos.add(todo);
                        todoMap.put(todo.getKindID(), todos);
                        cursor.moveToNext();
                    }
                }
            }
            cursor.close();
            updateTodoMap = false;
            return todoMap;
        }else{
            return todoMap;
        }
    }

    public void addKind(final String kind){
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(_KIND, kind);
            db.insert(TABLE_KIND, null, values);
        }catch(SQLiteException e){
            Log.e(TAG, "ADD KIND ERROR: " + kind, e);
        }finally{
            Log.d(TAG, "ADD KIND: " + kind);
        }
        updateKindList = true;
    }

    public void addTodo(final Todo todo){
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(_KIND, todo.getKindID());
            values.put(_TODO_JSON, todo.toJson());
            db.insert(TABLE_TODO, null, values);
        }catch(SQLiteException e){
            Log.e(TAG, "ADD TODO ERROR: " + todo.toJson(), e);
        }finally{
            Log.d(TAG, "ADD TODO: " + todo.toJson());
        }
        updateTodoMap = true;
        notificationHandler.throwAddTodoNotification(todo, Long.toString(todo.getId()), todo.getDescription());
    }

    public void deleteKind(Kind kind){
        kindList.remove(kind);
        try{
            SQLiteDatabase db = getWritableDatabase();
            db.delete(TABLE_KIND, _ID + " = ?", new String[] {
                    Long.toString(kind.getId())
            });
        }catch(SQLiteException e){
            Log.e(TAG, "DELETE KIND ERROR: ", e);
        }finally{
            Log.d(TAG, "DELETE KIND: " + kind.getName());
        }
    }

    public void deleteTodo(Todo todo){
        todoMap.get(todo.getKindID()).remove(todo);
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
        notificationHandler.throwDeleteTodoNotification(todo, Long.toString(todo.getId()), todo.getDescription());
    }

    public void updateKind(Kind kind, String newKindName){
        try{
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(_KIND, newKindName);
        db.update(TABLE_KIND, values, _ID + " = ?", new String[] {
                Long.toString(kind.getId())});
        }catch(Throwable t){
            Log.d(TAG, "UPDATE ERROR KIND from " + kind.getName() + " to " +  newKindName);
        }finally {
            Log.d(TAG, "UPDATE KIND from: " + kind.getName()+ " to: " + newKindName);
        }
        kind.setName(newKindName);
    }

    public void updateTodo(Todo newTodo){
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(_TODO_JSON, newTodo.toJson());
            db.update(TABLE_TODO, values, _ID + " = ?", new String[] {
                    Long.toString(newTodo.getId())});
        }catch(SQLiteException e){
            Log.e(TAG, "UPDATE ERROR TODO ", e);
        }finally{
            Log.d(TAG, "UPDATE TODO " + newTodo.toJson());
        }
        notificationHandler.throwUpdateTodoNotification(newTodo, Long.toString(newTodo.getId()), newTodo.getDescription());
    }
}
