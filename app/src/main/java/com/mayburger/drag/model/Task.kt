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
    @ColumnInfo(name = "caption")
    var caption: String? = null,
    @ColumnInfo(name = "image")
    var image: String? = "",
    @ColumnInfo(name = "language")
    var language: String? = "bahasa",
    @ColumnInfo(name = "state")
    var state: String? = "in_progress",
    @ColumnInfo(name= "order")
    var order:Int?=0
) : Parcelable