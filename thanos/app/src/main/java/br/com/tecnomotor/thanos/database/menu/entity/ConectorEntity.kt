package br.com.tecnomotor.thanos.database.menu.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CONECTOR")
data class ConectorEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "CONID") val id: Long,
    @ColumnInfo(name = "CONNOME") val nome: String
)