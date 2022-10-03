package br.com.tecnomotor.thanos.database.menu.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "PLATAFORMA_APLICACAO",
    primaryKeys = arrayOf(
        "PLAID",
        "APLID"
    ),
    foreignKeys = arrayOf(
        ForeignKey(
            entity = PlataformaEntity::class,
            childColumns = ["PLAID"],
            parentColumns = ["PLAID"]
        ),
        ForeignKey(
            entity = AplicacaoEntity::class,
            childColumns = ["APLID"],
            parentColumns = ["APLID"]
        )
    )
)
data class PlataformaAplicacaoEntity(
    @ColumnInfo(name = "PLAID") val idPlataforma: Long,
    @ColumnInfo(name = "APLID") val idAplicacao: Long
)