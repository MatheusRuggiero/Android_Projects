package br.com.tecnomotor.thanos.database.cliente.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "relatorio_ecu",
    foreignKeys = arrayOf(
        ForeignKey(entity = RelatorioAplicacaoEntity::class,
            parentColumns = arrayOf("REA_ID"),
            childColumns = arrayOf("REL_ID"),
            onDelete = ForeignKey.CASCADE)
    ))
data class RelatorioEcuEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "REC_ID") val id: Long,
    @ColumnInfo(name = "REL_ID") val idRel: Long,
    @ColumnInfo(name = "REC_DESCRICAO") val descricao: String,
    @ColumnInfo(name = "REC_VALOR") val valor: String?,
    @ColumnInfo(name = "REC_DATAHORA") val dataHora: Calendar,
)
