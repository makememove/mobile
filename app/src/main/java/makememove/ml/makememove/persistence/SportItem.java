package makememove.ml.makememove.persistence;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;

@Entity(tableName = "sportitem")
public class SportItem {
    public enum Category {
        Shuttle_Badminton, Tennis, Football, Basket_ball, Table_tennis, Chess, Hockey;

        @TypeConverter
        public static Category getByOrdinal(int ordinal) {
            Category ret = null;
            for (Category cat : Category.values()) {
                if (cat.ordinal() == ordinal) {
                    ret = cat;
                    break;
                }
            }
            return ret;
        }

        @TypeConverter
        public static int toInt(Category category) {
            return category.ordinal();
        }
    }
    @ColumnInfo(name = "category")
    public Category category;

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public Long id;
}
