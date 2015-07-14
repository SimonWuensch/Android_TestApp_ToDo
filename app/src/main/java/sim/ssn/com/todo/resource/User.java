package sim.ssn.com.todo.resource;

import com.owlike.genson.Genson;
import com.owlike.genson.annotation.JsonIgnore;

/**
 * Created by Simon on 14.07.2015.
 */
public class User {

    public static String DEFAULTUSERNAME = "defaultUser";
    public static String DEFAULTPASSWORD = "defaultPassword";

    private long id;
    private String name;
    private String password;

    public static User defaultUser(){
        return new User(DEFAULTUSERNAME, DEFAULTPASSWORD);
    }

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public static User JsonToUser(String jsonString){
        Genson genson = new Genson();
        return genson.deserialize(jsonString, User.class);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isDefaultUser(){
        return name.equals(DEFAULTUSERNAME);
    }
}
