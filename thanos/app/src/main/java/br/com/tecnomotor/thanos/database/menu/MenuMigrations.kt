package br.com.tecnomotor.thanos.database.menu

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.tecnomotor.thanos.database.DBUtil
import br.com.tecnomotor.thanos.database.DBUtil.Companion.toExisting

class MenuMigrations {
    companion object {
        @JvmField
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                DBUtil.alterTable(
                    database,
                    tableName = "MONTADORA",
                    columns = mapOf(
                        "MONNOME TEXT".toExisting(),
                        "MONSPA TEXT".toExisting(),
                        "MONENG TEXT".toExisting()
                    ),
                    primaryKeys = listOf()
                )
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Book ADD COLUMN pub_year INTEGER")
            }
        }

    }
}