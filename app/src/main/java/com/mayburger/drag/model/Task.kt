package com.mayburger.drag.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "tasks")
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "title")
    var title: String? = null,
    @ColumnInfo(name = "image")
    var image: String? = "",
    @ColumnInfo(name = "type")
    var type: String? = "",
    @ColumnInfo(name = "current_progress")
    var currentProgress: Float = 0f,
    @ColumnInfo(name = "target_progress")
    var target_progress: Float = 0f,
    @ColumnInfo(name = "state")
    var state: String? = "in_progress",
    @ColumnInfo(name = "order")
    var order: Int? = 0
) : Parcelable