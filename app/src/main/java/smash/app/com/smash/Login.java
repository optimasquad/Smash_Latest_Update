package smash.app.com.smash;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by renukum on 11/9/2017.
 */
@Entity
public class Login {
    @PrimaryKey
    public final int id;
    public String name;
    public String password;



    public Login(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password  = password;
    }

}
