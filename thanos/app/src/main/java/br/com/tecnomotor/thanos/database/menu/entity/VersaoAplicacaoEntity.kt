package br.com.tecnomotor.thanos.database.menu.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "VERSAO_APLICACAO",
    primaryKeys = arrayOf(
        "VERID",
        "APLID"
    ),
    foreignKeys = arrayOf(
        ForeignKey(
            entity = VersaoEntity::class,
            childColumns = ["VERID"],
            parentColumns = ["VERID"]
        ),
        ForeignKey(
            entity = AplicacaoEntity::class,
            childColumns = ["APLID"],
            parentColumns = ["APLID"]
        )
    )
)
data class VersaoAplicacaoEntity(
    @ColumnInfo(name = "VERID") val idVersao: Long,
    @ColumnInfo(name = "APLID") val idAplicacao: Long,
    @ColumnInfo(name = "VAMODULO") val checkModulo: String
)