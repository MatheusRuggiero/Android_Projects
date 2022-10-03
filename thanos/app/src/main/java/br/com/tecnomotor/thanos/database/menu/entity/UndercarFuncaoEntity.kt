package br.com.tecnomotor.thanos.database.menu.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "UNDERCAR_FUNCAO",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = UndercarEntity::class,
            childColumns = ["UNDMSG"],
            parentColumns = ["UNDMSG"]
        )
    )
)
data class UndercarFuncaoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "UNFID") val id: Long,
    @ColumnInfo(name = "UNDMSG") val idUndercar: Long,
    @ColumnInfo(name = "UNFMSG") val idFuncao: Long
)