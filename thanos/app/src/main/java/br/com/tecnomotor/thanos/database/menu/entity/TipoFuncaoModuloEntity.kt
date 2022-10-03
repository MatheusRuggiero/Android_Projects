package br.com.tecnomotor.thanos.database.menu.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TIPOFUNCAOMODULO")
data class TipoFuncaoModuloEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "TFMID") val id: Long,
    @ColumnInfo(name = "TFMNAME") val nome: String?,
    @ColumnInfo(name = "TFMMSG") val idMsg: Long
)