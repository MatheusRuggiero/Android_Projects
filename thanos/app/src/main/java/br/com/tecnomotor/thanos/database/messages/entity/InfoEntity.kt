package br.com.tecnomotor.thanos.database.messages.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "INFO_TAB")
data class InfoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id") val _id: Int,
    @ColumnInfo(name = "INFO_IDX") val INFO_IDX: Long?,
    @ColumnInfo(name = "INFO_PT_SHORT") val valuePtShort: String?,
    @ColumnInfo(name = "INFO_PT_MED") val valuePtMed: String?,
    @ColumnInfo(name = "INFO_PT_LONG") val valuePtLong: String?,
    @ColumnInfo(name = "INFO_ES_SHORT") val valueEsShort: String?,
    @ColumnInfo(name = "INFO_ES_MED") val valueEsMed: String?,
    @ColumnInfo(name = "INFO_ES_LONG") val valueEsLong: String?,
    @ColumnInfo(name = "INFO_EN_SHORT") val valueEnShort: String?,
    @ColumnInfo(name = "INFO_EN_MED") val valueEnMed: String?,
    @ColumnInfo(name = "INFO_EN_LONG") val valueEnLong: String?
)