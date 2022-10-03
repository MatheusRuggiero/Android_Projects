package br.com.tecnomotor.thanos.database.menu.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import java.io.Serializable

@Entity(
    tableName = "APLICACAO_CONECTOR",
    primaryKeys = arrayOf(
        "APLID",
        "CONID"
    ),
    foreignKeys = arrayOf(
        ForeignKey(
            entity = AplicacaoEntity::class,
            childColumns = ["APLID"],
            parentColumns = ["APLID"]
        ),
        ForeignKey(
            entity = ConectorEntity::class,
            childColumns = ["CONID"],
            parentColumns = ["CONID"]
        )
    )
)
data class AplicacaoConectorEntity(
    @ColumnInfo(name = "APLID") val idAplicacao: Long,
    @ColumnInfo(name = "CONID") val idConector: Long,
    @ColumnInfo(name = "IMGAPLCONV") val imgConVeiculo: Long?,
    @ColumnInfo(name = "IMGAPLCONJ") val imgConJogoPinos: Long?,
    @ColumnInfo(name = "APLCONPINOX") val pinoX: String,
    @ColumnInfo(name = "APLCONPINOY") val pinoY: String
)