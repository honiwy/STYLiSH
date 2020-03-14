package student.rachel.stylish.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [DesireProduct::class], version = 1,  exportSchema = false)
abstract class LocalDatabase : RoomDatabase(){
    abstract val localDatabaseDao: LocalDatabaseDao
    companion object {
        @Volatile
        private var INSTANCE:  LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase {
            synchronized(this) {
                var instance =
                    INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LocalDatabase::class.java,
                        "desire_product_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}