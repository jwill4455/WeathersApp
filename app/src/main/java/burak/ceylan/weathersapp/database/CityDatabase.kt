package burak.ceylan.weathersapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import burak.ceylan.weathersapp.database.entity.CityEntity

@Database(entities = [CityEntity::class], version = 1, exportSchema = false)
abstract class CityDatabase : RoomDatabase() {

    abstract fun getCityDao(): CityDao

    //Singleton
    companion object {
        @Volatile
        private var dbInstance: CityDatabase? = null
        fun getGithubDBInstance(context: Context): CityDatabase {
            if (dbInstance == null) {
                dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    CityDatabase::class.java,
                    "CityDatabase"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return dbInstance!!
        }
    }


}