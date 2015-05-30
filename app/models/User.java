package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity
public class User extends Model {

    @Id
    @Constraints.Required
    public String name;

    public String password;

    public static Finder<String, User> find = new Finder<String,User>(
            String.class, User.class
    );

    public static void create(String name, String password) {
        User user = new User();
        user.name = name;
        user.password = password;
        user.save();
    }

    public String validate() {
        if (authenticate(name, password)) {
            return null;
        }
        return "Invalid user and password";
    }

    private Boolean authenticate(String name, String password) {
        User user = find.where().eq("name", name).eq("password", password).findUnique();
        return (user != null);
    }
}