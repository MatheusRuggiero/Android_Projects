package br.com.tecnomotor.thanos.database.menu.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SISTEMA")
data class SistemaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "SISID") val id: Long,
    @ColumnInfo(name = "SISNOME") val nome: String,
    @ColumnInfo(name = "SISSPA") val nomeSpa: String?,
    @ColumnInfo(name = "SISENG") val nomeEng: String?
)
