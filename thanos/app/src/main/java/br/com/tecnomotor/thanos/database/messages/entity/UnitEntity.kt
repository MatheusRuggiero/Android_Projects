package br.com.tecnomotor.thanos.database.messages.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UNIT_TAB")
data class UnitEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id") val idControle: Long,
    @ColumnInfo(name = "UNIT_IDX") val UNIT_IDX: Long?,
    @ColumnInfo(name = "UNIT_PT_SHORT") val valuePtShort: String?,
    @ColumnInfo(name = "UNIT_PT_MED") val valuePtMed: String?,
    @ColumnInfo(name = "UNIT_PT_LONG") val valuePtLong: String?,
    @ColumnInfo(name = "UNIT_ES_SHORT") val valueEsShort: String?,
    @ColumnInfo(name = "UNIT_ES_MED") val valueEsMed: String?,
    @ColumnInfo(name = "UNIT_ES_LONG") val valueEsLong: String?,
    @ColumnInfo(name = "UNIT_EN_SHORT") val valueEnShort: String?,
    @ColumnInfo(name = "UNIT_EN_MED") val valueEnMed: String?,
    @ColumnInfo(name = "UNIT_EN_LONG") val valueEnLong: String?
)