package com.mayburger.drag.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "flyers")
@Parcelize
data class Flyer(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "title")
    var title: String? = null,
    @ColumnInfo(name = "caption")
    var caption: String? = null,
    @ColumnInfo(name = "image")
    var image: String? = "",
    @ColumnInfo(name = "language")
    var language: String? = "bahasa",
    @ColumnInfo(name = "state")
    var state: String? = "in_progress"
) : Parcelable