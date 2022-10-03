package br.com.tecnomotor.thanos.database.menu.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UNDERCAR")
data class UndercarEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "UNDMSG") val idMsg: Long,
    @ColumnInfo(name = "UNDCHECK") val check: String,
)