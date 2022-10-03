package br.com.tecnomotor.thanos.database.menu.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TIPOSISTEMA")
data class TipoSistemaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "TPSID") val id: Long,
    @ColumnInfo(name = "TPSNOME") val nome: String,
    @ColumnInfo(name = "TPSSPA") val nomeSpa: String?,
    @ColumnInfo(name = "TPSENG") val nomeEng: String?
)