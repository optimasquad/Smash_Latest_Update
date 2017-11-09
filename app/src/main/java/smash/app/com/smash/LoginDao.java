package smash.app.com.smash;

/**
 * Created by renukum on 11/9/2017.
 */

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface LoginDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUser(Login user);

    @Query("select * from login")
    public List<Login> getAllUser();

   /* @Query("select * from user where id = :userId")
    public List<Login> getUser(long userId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateUser(Login user);*/

    @Query("delete from login")
    void removeAllUsers();

    @Query("select * from login where id = :userId")
    public List<Login> getUser(long userId);


    @Query("select * from eventObjects")
    public List<EventObjects> getAllEvents();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addEntity(EventObjects eventObjects);


}