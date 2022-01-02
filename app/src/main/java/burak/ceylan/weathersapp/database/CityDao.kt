package burak.ceylan.weathersapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import burak.ceylan.weathersapp.database.entity.CityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Insert
    suspend fun insertCity(favoriteUser: CityDao)

    @Query("SELECT * FROM City")
    fun getAllCity(): LiveData<List<CityEntity>>

    @Query("DELETE FROM City WHERE City.id = :id")
    suspend fun removeCity(id: Int): Int
}
