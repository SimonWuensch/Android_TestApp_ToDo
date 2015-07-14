package sim.ssn.com.todo.resource;

import com.owlike.genson.Genson;
import com.owlike.genson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

/**
 * Created by Simon on 04.06.2015.
 */
public class Todo {

    @JsonIgnore
    private long id;
    private String description;
    private boolean important;
    private boolean finished;
    private long kindID;
    private Date date;
    private Date memory;
    private List<String> subTask;
    private String node;
    private int color;

    public Todo(long kindID, String description){
        this.description = description;
        this.kindID = kindID;
    }

    public Todo(){
    }
    @JsonIgnore
    public long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(long id) {
        this.id = id;
    }

    public String toJson(){
        Genson genson = new Genson();
        return genson.serialize(this);
    }

    public static Todo JsonToTodo(String jsonString){
        Genson genson = new Genson();
        return genson.deserialize(jsonString, Todo.class);
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public boolean toggleImportant(){
        setImportant(isImportant() ? false : true);
        return isImportant();
    }

    public long getKindID() {
        return kindID;
    }

    public void setKindID(long kindID) {
        this.kindID = kindID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getMemory() {
        return memory;
    }

    public void setMemory(Date memory) {
        this.memory = memory;
    }

    public List<String> getSubTask() {
        return subTask;
    }

    public void setSubTask(List<String> subTask) {
        this.subTask = subTask;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
