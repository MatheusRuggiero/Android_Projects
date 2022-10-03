package br.com.tecnomotor.thanos.database.menu.entity

import androidx.room.ColumnInfo

data class SistemaAnoEntity(
    @ColumnInfo(name = "SISID") val id: Long,
    @ColumnInfo(name = "SISNOME") val nome: String,
    @ColumnInfo(name = "SISSPA") val nomeSpa: String?,
    @ColumnInfo(name = "SISENG") val nomeEng: String?,
    @ColumnInfo(name = "APLANOINICIAL") val anoInicial: Long?,
    @ColumnInfo(name = "APLANOFINAL") val anoFinal: Long?
)