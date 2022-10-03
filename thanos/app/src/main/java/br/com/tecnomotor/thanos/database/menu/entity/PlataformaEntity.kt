package br.com.tecnomotor.thanos.database.menu.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PLATAFORMA")
data class PlataformaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "PLAID") val id: Long,
    @ColumnInfo(name = "PLANOME") val nome: String,
    @ColumnInfo(name = "PLAVERHABILITADA") val versaoHabilitada: Long // última versão de aplicação habilitada nesta plataforma
)