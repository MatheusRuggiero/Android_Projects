package br.com.tecnomotor.thanos.database.menu.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "IDIOMA")
data class IdiomaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IDIID") val id: Long,
    @ColumnInfo(name = "IDISIGLA") val sigla: String,
    @ColumnInfo(name = "IDINOME") val nome: String
)