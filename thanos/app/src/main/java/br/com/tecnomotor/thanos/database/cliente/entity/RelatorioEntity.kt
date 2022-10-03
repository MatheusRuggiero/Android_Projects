package br.com.tecnomotor.thanos.database.cliente.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "relatorio")
data class RelatorioEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "REL_ID") val id: Long,
    @ColumnInfo(name = "REL_NUMSERIE") val numserie: String,
    @ColumnInfo(name = "REL_VERFIRMWARE") val verfirmware: String,
    @ColumnInfo(name = "REL_PLATAFORMA") val plataforma: String,
    @ColumnInfo(name = "REL_VERSAO") val versao: Long,
    @ColumnInfo(name = "REL_VERSAOAPP") val versaoapp: String,
    @ColumnInfo(name = "REL_DELETADO") val deletado: Boolean,
    )
