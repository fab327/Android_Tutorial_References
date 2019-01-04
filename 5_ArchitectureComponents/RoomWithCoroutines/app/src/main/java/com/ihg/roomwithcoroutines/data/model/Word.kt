package com.ihg.roomwithcoroutines.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/*
 * tableName & columnInfo are only needed if we want them different than the class/property's name
 */
@Entity(tableName = "word_table")
data class Word(@PrimaryKey @ColumnInfo(name = "word") val word: String)