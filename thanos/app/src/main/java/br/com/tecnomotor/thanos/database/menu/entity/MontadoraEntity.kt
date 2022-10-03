package br.com.tecnomotor.thanos.database.menu.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MONTADORA")
data class MontadoraEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "MONID") val id: Long,
    @ColumnInfo(name = "MONNOME") val nome: String,
    @ColumnInfo(name = "MONSPA") val nomeSpa: String? = null,
    @ColumnInfo(name = "MONENG") val nomeEng: String? = null
)