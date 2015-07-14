package sim.ssn.com.todo.resource;

/**
 * Created by Simon on 14.07.2015.
 */
public class User {

    public static String DEFAULTUSERNAME = "defaultUser";
    public static String DEFAULTPASSWORD = "defaultPassword";

    private String userName;
    private String password;

    public static User defaultUser(){
        return new User(DEFAULTUSERNAME, DEFAULTPASSWORD);
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isDefaultUser(){
        return userName.equals(DEFAULTUSERNAME) || password.equals(DEFAULTPASSWORD);
    }
}
