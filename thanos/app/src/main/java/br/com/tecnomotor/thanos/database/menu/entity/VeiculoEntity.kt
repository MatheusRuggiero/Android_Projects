package br.com.tecnomotor.thanos.database.menu.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "VEICULO")
data class VeiculoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "VEIID") val id: Long,
    @ColumnInfo(name = "VEINOME") val nome: String,
    @ColumnInfo(name = "VEISPA") val nomeSpa: String? = null,
    @ColumnInfo(name = "VEIENG") val nomeEng: String? = null
)