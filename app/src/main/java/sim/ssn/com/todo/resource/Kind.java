package sim.ssn.com.todo.resource;

/**
 * Created by Simon on 06.06.2015.
 */
public class Kind {

    private long id;
    private String name;

    public Kind(long id, String kind) {
        this.id = id;
        this.name = kind;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String kind) {
        this.name = kind;
    }

    public String toString(){
        return new StringBuilder()//
                .append("ID: ").append(id).append(" ")//
                .append("KindName: ").append(name).toString();
    }
}
