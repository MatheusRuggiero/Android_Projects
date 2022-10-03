package br.com.tecnomotor.thanos.database.messages.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "VALUE_TAB")
data class ValueEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id") val idControle: Int,
    @ColumnInfo(name = "VALUE_IDX") val id: Long?,
    @ColumnInfo(name = "VALUE_PT_SHORT") val valuePtShort: String?,
    @ColumnInfo(name = "VALUE_PT_MED") val valuePtMed: String?,
    @ColumnInfo(name = "VALUE_PT_LONG") val valuePtLong: String?,
    @ColumnInfo(name = "VALUE_ES_SHORT") val valueEsShort: String?,
    @ColumnInfo(name = "VALUE_ES_MED") val valueEsMed: String?,
    @ColumnInfo(name = "VALUE_ES_LONG") val valueEsLong: String?,
    @ColumnInfo(name = "VALUE_EN_SHORT") val valueEnShort: String?,
    @ColumnInfo(name = "VALUE_EN_MED") val valueEnMed: String?,
    @ColumnInfo(name = "VALUE_EN_LONG") val valueEnLong: String?
) {
    override fun toString(): String {
        return "ValueEntity(idControle=$idControle, id=$id, valuePtShort=$valuePtShort, valuePtMed=$valuePtMed, valuePtLong=$valuePtLong, valueEsShort=$valueEsShort, valueEsMed=$valueEsMed, valueEsLong=$valueEsLong, valueEnShort=$valueEnShort, valueEnMed=$valueEnMed, valueEnLong=$valueEnLong)"
    }
}