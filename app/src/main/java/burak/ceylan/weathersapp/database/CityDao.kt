package burak.ceylan.weathersapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import burak.ceylan.weathersapp.database.entity.CityEntity

@Dao
interface CityDao {
    @Insert
    suspend fun insertCity(city: CityEntity)

    @Query("SELECT * FROM City")
    fun getAllCity(): LiveData<List<CityEntity>>

    @Query("DELETE FROM City WHERE City.id = :id")
    suspend fun removeCity(id: Int): Int



}
