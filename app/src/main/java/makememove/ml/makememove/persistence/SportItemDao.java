package makememove.ml.makememove.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface SportItemDao {
        @Query("SELECT * FROM sportitem")
        List<SportItem> getAll();

        @Insert
        long insert(SportItem sportItems);

        @Update
        void update(SportItem sportItem);

        @Delete
        void deleteItem(SportItem sportItem);
}
