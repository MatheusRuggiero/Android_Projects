package br.com.tecnomotor.thanos.database.cliente.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "historico")
data class HistoricoEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "hismonid") val montadoraId: Long,
    @ColumnInfo(name = "hismonnome") val montadoraNome: String,
    @ColumnInfo(name = "hisveiid") val veiculoId: Long,
    @ColumnInfo(name = "hisveinome") val veiculoNome: String,
    @ColumnInfo(name = "hismotid") val motorizacaoId: Long,
    @ColumnInfo(name = "hismotnome") val motorizacaoNome: String,
    @ColumnInfo(name = "histpsid") val tipoSistemaId: Long,
    @ColumnInfo(name = "histpsnome") val tipoSistemaNome: String,
    @ColumnInfo(name = "hissisid") val sistemaId: Long,
    @ColumnInfo(name = "hissisnome") val sistemaNome: String,
    @ColumnInfo(name = "hisconid") val conectorId: Long,
    @ColumnInfo(name = "hisconnome") val conectorNome: String,
    @ColumnInfo(name = "hisposconector") val posicaoConector: String,
    @ColumnInfo(name = "hispinox") val pinoX: String,
    @ColumnInfo(name = "hispinoy") val pinoY: String,
    @ColumnInfo(name = "hisaplid") val aplicacaoId: Long,
    @ColumnInfo(name = "hismodulo") val modulo: Long,
    @ColumnInfo(name = "hisanoinicial") val anoInicial: Long,
    @ColumnInfo(name = "hisanofinal") val anoFinal: Long,
    @ColumnInfo(name = "hisimgconvei") val imgConVeiculo: Long,
    @ColumnInfo(name = "hisdata", defaultValue = "CURRENT_TIMESTAMP") val data: Date
)