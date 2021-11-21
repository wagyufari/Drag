package com.mayburger.drag.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "state")
@Parcelize
data class State(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "title")
    var title: String = "",
    @ColumnInfo(name = "order")
    var order:Int = 0,
    @ColumnInfo(name = "state_id")
    var stateId: String = Integer.MAX_VALUE.toString(),
) : Parcelable