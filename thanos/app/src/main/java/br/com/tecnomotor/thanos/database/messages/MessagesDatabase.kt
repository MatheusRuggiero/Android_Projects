package br.com.tecnomotor.thanos.database.messages

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.tecnomotor.thanos.database.DATABASE_PATH
import br.com.tecnomotor.thanos.database.MSG_DATABASE_NAME
import br.com.tecnomotor.thanos.database.menu.MenuDatabase
import br.com.tecnomotor.thanos.database.messages.dao.*
import br.com.tecnomotor.thanos.database.messages.entity.*

@Database(
    entities = arrayOf(
        CondDtcEntity::class,
        DtcEntity::class,
        InfoEntity::class,
        MenuEntity::class,
        StateEntity::class,
        UnitEntity::class,
        ValueEntity::class
    ),
    version = 1
)
abstract class MessagesDatabase : RoomDatabase() {
    abstract fun condDtcDao(): CondDtcDao
    abstract fun dtcDao(): DtcDao
    abstract fun infoDao(): InfoDao
    abstract fun menuDao(): MenuDao
    abstract fun stateDao(): StateDao
    abstract fun unitDao(): UnitDao
    abstract fun valueDao(): ValueDao

    companion object { // singleton
        @Volatile private var instance: MessagesDatabase? = null

        //Singleton instance
        fun getInstance(context: Context? = null): MessagesDatabase {
            if (instance == null)
                if (context == null)
                    throw java.lang.Exception("O contexto deve ser enviado pelo menos na primeira vez que o método getInstance(contexto) for chamado")
                else
                    synchronized(this) {
                        buildDatabase(context.applicationContext).also {
                            instance = it
                        }
                    }
            return instance!!
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): MessagesDatabase {
            return Room.databaseBuilder(context, MessagesDatabase::class.java, MSG_DATABASE_NAME)
                .createFromAsset(DATABASE_PATH + MSG_DATABASE_NAME)
//                .allowMainThreadQueries()
//                .addMigrations(MenuMigrations.MIGRATION_1_2)
//                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
