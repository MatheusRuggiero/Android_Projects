package br.com.tecnomotor.thanos.database.cliente.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "favorito")
data class FavoritoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "favmonid") val montadoraId: Long,
    @ColumnInfo(name = "favmonnome") val montadoraNome: String,
    @ColumnInfo(name = "favveiid") val veiculoId: Long,
    @ColumnInfo(name = "favveinome") val veiculoNome: String,
    @ColumnInfo(name = "favmotid") val motorizacaoId: Long,
    @ColumnInfo(name = "favmotnome") val motorizacaoNome: String,
    @ColumnInfo(name = "favtpsid") val tipoSistemaId: Long,
    @ColumnInfo(name = "favtpsnome") val tipoSistemaNome: String,
    @ColumnInfo(name = "favsisid") val sistemaId: Long,
    @ColumnInfo(name = "favsisnome") val sistemaNome: String,
    @ColumnInfo(name = "favconid") val conectorId: Long,
    @ColumnInfo(name = "favconnome") val conectorNome: String,
    @ColumnInfo(name = "favposconector") val posicaoConector: String,
    @ColumnInfo(name = "favpinox") val pinoX: String,
    @ColumnInfo(name = "favpinoy") val pinoY: String,
    @ColumnInfo(name = "favaplid") val aplicacaoId: Long,
    @ColumnInfo(name = "favmodulo") val modulo: Long,
    @ColumnInfo(name = "favanoinicial") val anoInicial: Long,
    @ColumnInfo(name = "favanofinal") val anoFinal: Long,
    @ColumnInfo(name = "favimgconvei") val imgConVeiculo: Long,
    @ColumnInfo(name = "favdata", defaultValue = "CURRENT_TIMESTAMP") val data: Date
)