package br.com.tecnomotor.thanos.database.cliente.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "relatorio_codigo_defeitos",
    foreignKeys = arrayOf(
        ForeignKey(entity = RelatorioAplicacaoEntity::class,
        parentColumns = arrayOf("REA_ID"),
        childColumns = arrayOf("REA_ID"),
        onDelete = ForeignKey.CASCADE)
    ))
data class RelatorioCodigoDefeitosEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "RCD_ID") val id: Long,
    @ColumnInfo(name = "REA_ID") val idRea: Long,
    @ColumnInfo(name = "RCD_CODIGO") val codigo: String?,
    @ColumnInfo(name = "RCD_DESCRICAO") val descricao: String,
    @ColumnInfo(name = "RCD_SINTOMA") val sintoma: String?,
    @ColumnInfo(name = "RCD_STATUS") val status: String?,
    @ColumnInfo(name = "RCD_DATAHORA") val dataHora: Calendar,
)
