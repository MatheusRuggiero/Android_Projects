package br.com.tecnomotor.thanos.database.menu.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "VERSAO")
data class VersaoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "VERID") val id: Long
)