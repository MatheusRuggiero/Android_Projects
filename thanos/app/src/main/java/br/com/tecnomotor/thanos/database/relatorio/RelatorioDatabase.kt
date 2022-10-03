package br.com.tecnomotor.thanos.database.relatorio

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.tecnomotor.thanos.database.DATABASE_PATH
import br.com.tecnomotor.thanos.database.MENU_DATABASE_NAME
import br.com.tecnomotor.thanos.database.cliente.dao.RelatorioAplDao
import br.com.tecnomotor.thanos.database.menu.MenuDatabase
import br.com.tecnomotor.thanos.database.menu.dao.MontadoraDao

abstract class RelatorioDatabase : RoomDatabase() {

    abstract fun relatorioAplDao(): RelatorioAplDao

}