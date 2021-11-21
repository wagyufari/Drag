package com.mayburger.drag.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mayburger.drag.model.State
import com.mayburger.drag.model.Task

@Dao
interface StateDao {

    @Query("SELECT * FROM state")
    fun getStates():LiveData<List<State>>
    @Query("SELECT * FROM state")
    suspend fun getStatesSuspended():List<State>

    @Insert
    fun putState(state:State)

    @Delete
    suspend fun deleteTask(task:Task)

}