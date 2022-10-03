package br.com.tecnomotor.thanos.database.messages.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CONDDTC_TAB")
data class CondDtcEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id") val idControle: Long,
    @ColumnInfo(name = "CONDDTC_IDX") val id: Long?,
    @ColumnInfo(name = "CONDDTC_PT_SHORT") val valuePtShort: String?,
    @ColumnInfo(name = "CONDDTC_PT_MED") val valuePtMed: String?,
    @ColumnInfo(name = "CONDDTC_PT_LONG") val valuePtLong: String?,
    @ColumnInfo(name = "CONDDTC_ES_SHORT") val valueEsShort: String?,
    @ColumnInfo(name = "CONDDTC_ES_MED") val valueEsMed: String?,
    @ColumnInfo(name = "CONDDTC_ES_LONG") val valueEsLong: String?,
    @ColumnInfo(name = "CONDDTC_EN_SHORT") val valueEnShort: String?,
    @ColumnInfo(name = "CONDDTC_EN_MED") val valueEnMed: String?,
    @ColumnInfo(name = "CONDDTC_EN_LONG") val valueEnLong: String?
)