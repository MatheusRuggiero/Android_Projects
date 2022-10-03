package br.com.tecnomotor.thanos.database.menu.entity

import androidx.room.ColumnInfo

data class ConectorAplicacaoEntity(
    @ColumnInfo(name = "CONID") val id: Long,
    @ColumnInfo(name = "CONNOME") val nome: String,
    @ColumnInfo(name = "IMGAPLCONV") val imgConVeiculo: Long?,
    @ColumnInfo(name = "IMGAPLCONJ") val imgConJogoPinos: Long?,
    @ColumnInfo(name = "APLCONPINOX") val pinoX: String,
    @ColumnInfo(name = "APLCONPINOY") val pinoY: String,
    @ColumnInfo(name = "APLPOSCONECTOR") val posConector: String?
)