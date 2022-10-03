package br.com.tecnomotor.thanos.database.messages.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "STATE_TAB")
data class StateEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id") val idControle: Long,
    @ColumnInfo(name = "STATE_IDX") val id: Long?,
    @ColumnInfo(name = "STATE_PT_SHORT") val valuePtShort: String?,
    @ColumnInfo(name = "STATE_PT_MED") val valuePtMed: String?,
    @ColumnInfo(name = "STATE_PT_LONG") val valuePtLong: String?,
    @ColumnInfo(name = "STATE_ES_SHORT") val valueEsShort: String?,
    @ColumnInfo(name = "STATE_ES_MED") val valueEsMed: String?,
    @ColumnInfo(name = "STATE_ES_LONG") val valueEsLong: String?,
    @ColumnInfo(name = "STATE_EN_SHORT") val valueEnShort: String?,
    @ColumnInfo(name = "STATE_EN_MED") val valueEnMed: String?,
    @ColumnInfo(name = "STATE_EN_LONG") val valueEnLong: String?
)