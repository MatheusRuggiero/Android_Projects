package br.com.tecnomotor.thanos.database.cliente.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "relatorio_aplicacao",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = RelatorioEntity::class,
            parentColumns = arrayOf("REL_ID"),
            childColumns = arrayOf("REL_ID"),
            onDelete = ForeignKey.CASCADE
        )
    )
)

data class RelatorioAplicacaoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "REA_ID") val id: Long,
    @ColumnInfo(name = "REL_ID") val idRel: Long,
    @ColumnInfo(name = "REA_PLACA") val placa: String?,
    @ColumnInfo(name = "REA_APLID") val aplicacaoId: Long,
    @ColumnInfo(name = "REA_MONID") val montadoraId: Long,
    @ColumnInfo(name = "REA_MONNOME") val montadoraNome: String,
    @ColumnInfo(name = "REA_VEIID") val veiculoId: Long,
    @ColumnInfo(name = "REA_VEINOME") val veiculoNome: String,
    @ColumnInfo(name = "REA_MOTID") val motorizacaoId: Long?,
    @ColumnInfo(name = "REA_MOTONOME") val motorizacaoNome: String?,
    @ColumnInfo(name = "REA_TPSID") val tipoSistemaId: Long,
    @ColumnInfo(name = "REA_TPSNOME") val tipoSistemaNome: String,
    @ColumnInfo(name = "REA_SISID") val sistemaId: Long,
    @ColumnInfo(name = "REA_SISNOME") val sistemaNome: String,
    @ColumnInfo(name = "REA_ANOINICIAL") val anoInical: Long?,
    @ColumnInfo(name = "REA_ANOFINAL") val anoFinal: Long?,
    @ColumnInfo(name = "REA_MODULO") val modulo: Long,
    @ColumnInfo(name = "REA_DATAHORA") val dataHora: Calendar,

    )