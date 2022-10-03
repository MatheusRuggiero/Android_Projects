package br.com.tecnomotor.thanos.database.messages.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MENU_TAB")
data class MenuEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id") val idControle: Long,
    @ColumnInfo(name = "MENU_IDX") val id: Long?,
    @ColumnInfo(name = "MENU_PT_SHORT") val valuePtShort: String?,
    @ColumnInfo(name = "MENU_PT_MED") val valuePtMed: String?,
    @ColumnInfo(name = "MENU_PT_LONG") val valuePtLong: String?,
    @ColumnInfo(name = "MENU_ES_SHORT") val valueEsShort: String?,
    @ColumnInfo(name = "MENU_ES_MED") val valueEsMed: String?,
    @ColumnInfo(name = "MENU_ES_LONG") val valueEsLong: String?,
    @ColumnInfo(name = "MENU_EN_SHORT") val valueEnShort: String?,
    @ColumnInfo(name = "MENU_EN_MED") val valueEnMed: String?,
    @ColumnInfo(name = "MENU_EN_LONG") val valueEnLong: String?
) {
    override fun toString(): String {
        return "Menu(idControle=$idControle, id=$id, valuePtShort=$valuePtShort, valuePtMed=$valuePtMed, valuePtLong=$valuePtLong, valueEsShort=$valueEsShort, valueEsMed=$valueEsMed, valueEsLong=$valueEsLong, valueEnShort=$valueEnShort, valueEnMed=$valueEnMed, valueEnLong=$valueEnLong)"
    }
}