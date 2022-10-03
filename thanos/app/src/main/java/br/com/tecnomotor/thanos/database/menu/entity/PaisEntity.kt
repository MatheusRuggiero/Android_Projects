package br.com.tecnomotor.thanos.database.menu.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PAIS")
data class PaisEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "PAIID") val id: Long,
    @ColumnInfo(name = "PAISIGLA") val sigla: String?,
    @ColumnInfo(name = "PAINOME") val nome: String?,
    @ColumnInfo(name = "PAISPA") val nomeSpa: String?,
    @ColumnInfo(name = "PAIENG") val nomeEng: String?,
)