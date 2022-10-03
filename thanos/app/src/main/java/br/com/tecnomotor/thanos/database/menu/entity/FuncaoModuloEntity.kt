package br.com.tecnomotor.thanos.database.menu.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "FUNCAOMODULO",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = TipoFuncaoModuloEntity::class,
            childColumns = ["TFMID"],
            parentColumns = ["TFMID"]
        )
    )
)
data class FuncaoModuloEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "FNMID") val id: Long,
    @ColumnInfo(name = "MODID") val idModulo: Long,
    @ColumnInfo(name = "TFMID") val idTipoFuncaoModulo: Long,
    @ColumnInfo(name = "FNMMSG") val idMsg: Long
)