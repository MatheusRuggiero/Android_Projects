package br.com.tecnomotor.thanos.database.cliente.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "relatorio_leituras",
    foreignKeys = arrayOf(
        ForeignKey(entity = RelatorioAplicacaoEntity::class,
            parentColumns = arrayOf("REA_ID"),
            childColumns = arrayOf("REA_ID"),
            onDelete = ForeignKey.CASCADE)
    ))
data class RelatorioLeiturasEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "RLE_ID") val id: Long,
    @ColumnInfo(name = "REA_ID") val idRea: Long,
    @ColumnInfo(name = "RLE_TIPO") val tipo: Long,
    @ColumnInfo(name = "RLE_NOME") val nome: String,
    @ColumnInfo(name = "RLE_VALOR_MINIMO") val valorMinimo: Double?,
    @ColumnInfo(name = "RLE_VALOR_MAXIMO") val valormaximo: Double?,
    @ColumnInfo(name = "RLE_VALOR") val valor: Double?,
    @ColumnInfo(name = "RLE_UNIDADE") val unidade: String?,
    @ColumnInfo(name = "RLE_VALOR_TEXTO") val valorTexto: String?,
    @ColumnInfo(name = "RLE_DATAHORA") val dataHora: Calendar,
)