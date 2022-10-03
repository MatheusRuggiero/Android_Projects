package br.com.tecnomotor.thanos.database.menu.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MOTORIZACAO")
data class MotorizacaoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "MOTID") val id: Long,
    @ColumnInfo(name = "MOTNOME") val nome: String?,
    @ColumnInfo(name = "MOTSPA") val nomeSpa: String?,
    @ColumnInfo(name = "MOTENG") val nomeEng: String?
)