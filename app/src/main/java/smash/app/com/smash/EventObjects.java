package smash.app.com.smash;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by jatinmahajan on 05/11/17.
 */
@Entity
public class EventObjects {

    @PrimaryKey
    private int id;

    private String message;


    private String start;


    private String end;


    public EventObjects(int id, String message, String start, String end) {
        this.start = start;
        this.message = message;
        this.id = id;
        this.end = end;
    }

    public String getEnd() {
        return end;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getStart() {
        return start;
    }
}

