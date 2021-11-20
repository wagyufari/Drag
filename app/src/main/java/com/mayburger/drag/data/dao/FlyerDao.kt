package com.mayburger.drag.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mayburger.drag.model.Flyer

@Dao
interface FlyerDao {
    @Query("SELECT * FROM flyers WHERE language LIKE :language AND state IS :state")
    fun getFlyers(language:String, state:String): LiveData<List<Flyer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun putFlyer(flyer: Flyer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun putFlyers(book:List<Flyer>)

    @Update
    suspend fun updateFlyer(flyer:Flyer)

    @Query("DELETE FROM flyers")
    suspend fun deleteFlyers()
}