package br.com.tecnomotor.thanos.database.messages

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class MessagesMigrations {
    companion object {
        @JvmField
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }

    }
}