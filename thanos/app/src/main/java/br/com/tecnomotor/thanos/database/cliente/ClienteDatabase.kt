package br.com.tecnomotor.thanos.database.cliente

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.tecnomotor.thanos.database.CLIENT_DATABASE_NAME
import br.com.tecnomotor.thanos.database.Converters
import br.com.tecnomotor.thanos.database.cliente.dao.*
import br.com.tecnomotor.thanos.database.cliente.entity.*

@Database(
    entities = arrayOf(
        HistoricoEntity::class,
        FavoritoEntity::class,
        RelatorioEntity::class,
        RelatorioAplicacaoEntity::class,
        RelatorioCodigoDefeitosEntity::class,
        RelatorioLeiturasEntity::class,
        RelatorioGraficosEntity::class,
        RelatorioEcuEntity::class
    ),
    version = 1
)
@TypeConverters(Converters::class)
abstract class ClienteDatabase : RoomDatabase() {
    abstract fun favoritosDao(): FavoritosDao
    abstract fun historicosDao(): HistoricosDao
    abstract fun relatoriosDao(): RelatoriosDao
    abstract fun relatoriosAplDao(): RelatorioAplDao
    abstract fun codigoDefeitosDao(): CodDefeitosDao
    abstract fun leiturasDao(): LeiturasDao
    abstract fun graficosDao(): GraficosDao
    abstract fun ecuDao(): EcuDao

    companion object { // singleton
        @Volatile
        private var instance: ClienteDatabase? = null

        //Singleton instance
        fun getInstance(context: Context? = null): ClienteDatabase {
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
        private fun buildDatabase(context: Context): ClienteDatabase {
            return Room.databaseBuilder(context, ClienteDatabase::class.java, CLIENT_DATABASE_NAME)
                .allowMainThreadQueries()
//                .addMigrations(ClienteMigrations.MIGRATION_1_2)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}