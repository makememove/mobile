package makememove.ml.makememove.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(
        entities = {SportItem.class},
        version = 2
)
@TypeConverters(value = {SportItem.Category.class})
public abstract class SportListDatabase extends RoomDatabase {
    public abstract SportItemDao sportItemDao();
    public abstract MyDao myDao();
}
