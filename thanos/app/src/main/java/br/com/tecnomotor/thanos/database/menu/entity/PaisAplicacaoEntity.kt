package br.com.tecnomotor.thanos.database.menu.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "PAIS_APLICACAO",
    primaryKeys = arrayOf(
        "PAIID",
        "APLID"
    ),
    foreignKeys = arrayOf(
        ForeignKey(
            entity = PaisEntity::class,
            childColumns = ["PAIID"],
            parentColumns = ["PAIID"]
        ),
        ForeignKey(
            entity = AplicacaoEntity::class,
            childColumns = ["APLID"],
            parentColumns = ["APLID"]
        )
    )
)
data class PaisAplicacaoEntity(
    @ColumnInfo(name = "PAIID") val PAIID: Long,
    @ColumnInfo(name = "APLID") val APLID: Long
)