package br.com.tecnomotor.thanos.database.cliente.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Blob
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "relatorio_graficos",
    foreignKeys = arrayOf(
        ForeignKey(entity = RelatorioAplicacaoEntity::class,
            parentColumns = arrayOf("REA_ID"),
            childColumns = arrayOf("REL_ID"),
            onDelete = ForeignKey.CASCADE)
    ))
data class RelatorioGraficosEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "REG_ID") val id: Long,
    @ColumnInfo(name = "REL_ID") val idRel: Long,
    @ColumnInfo(name = "REG_NOME") val nome: String,
    @ColumnInfo(name = "REG_IMG", typeAffinity = ColumnInfo.BLOB) val img: ByteArray,
    @ColumnInfo(name = "REG_DATAHORA") val dataHora: Calendar,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RelatorioGraficosEntity

        if (id != other.id) return false
        if (idRel != other.idRel) return false
        if (nome != other.nome) return false
        if (!img.contentEquals(other.img)) return false
        if (dataHora != other.dataHora) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + idRel.hashCode()
        result = 31 * result + nome.hashCode()
        result = 31 * result + img.contentHashCode()
        result = 31 * result + dataHora.hashCode()
        return result
    }
}

