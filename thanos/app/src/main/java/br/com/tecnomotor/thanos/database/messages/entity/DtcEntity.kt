package br.com.tecnomotor.thanos.database.messages.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DTC_TAB")
data class DtcEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id") val idControle: Long,
    @ColumnInfo(name = "DTC_IDX") val id: Long?,
    @ColumnInfo(name = "DTC_PT_SHORT") val valuePtShort: String?,
    @ColumnInfo(name = "DTC_PT_MED") val valuePtMed: String?,
    @ColumnInfo(name = "DTC_PT_LONG") val valuePtLong: String?,
    @ColumnInfo(name = "DTC_ES_SHORT") val valueEsShort: String?,
    @ColumnInfo(name = "DTC_ES_MED") val valueEsMed: String?,
    @ColumnInfo(name = "DTC_ES_LONG") val valueEsLong: String?,
    @ColumnInfo(name = "DTC_EN_SHORT") val valueEnShort: String?,
    @ColumnInfo(name = "DTC_EN_MED") val valueEnMed: String?,
    @ColumnInfo(name = "DTC_EN_LONG") val valueEnLong: String?
)