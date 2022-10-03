package br.com.tecnomotor.thanos.database.menu.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "APLICACAO",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = MontadoraEntity::class,
            childColumns = ["MONID"],
            parentColumns = ["MONID"]
        ),
        ForeignKey(
            entity = VeiculoEntity::class,
            childColumns = ["VEIID"],
            parentColumns = ["VEIID"]
        ),
        ForeignKey(
            entity = MotorizacaoEntity::class,
            childColumns = ["MOTID"],
            parentColumns = ["MOTID"]
        ),
        ForeignKey(
            entity = TipoSistemaEntity::class,
            childColumns = ["TPSID"],
            parentColumns = ["TPSID"]
        ),
        ForeignKey(
            entity = SistemaEntity::class,
            childColumns = ["SISID"],
            parentColumns = ["SISID"]
        )
    )
)
data class AplicacaoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "APLID") val id: Long,
    @ColumnInfo(name = "MONID") val idMontadora: Long,
    @ColumnInfo(name = "VEIID") val idVeiculo: Long,
    @ColumnInfo(name = "MOTID") val idMotorizacao: Long?,
    @ColumnInfo(name = "TPSID") val idTipoSistema: Long,
    @ColumnInfo(name = "SISID") val idSistema: Long,
    @ColumnInfo(name = "APLANOINICIAL") val anoInicial: Long?,
    @ColumnInfo(name = "APLANOFINAL") val anoFinal: Long?,
    @ColumnInfo(name = "APLPOSCONECTOR") val posConector: String?,
    @ColumnInfo(name = "APLMODULO") val modulo: Long,
    @ColumnInfo(name = "APLREP") val reparacao: Long?,
    @ColumnInfo(name = "APLMANOPERACAO") val manOperacao: String?
)