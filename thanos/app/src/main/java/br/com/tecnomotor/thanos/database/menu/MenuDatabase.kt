package br.com.tecnomotor.thanos.database.menu

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.tecnomotor.thanos.database.DATABASE_PATH
import br.com.tecnomotor.thanos.database.MENU_DATABASE_NAME
import br.com.tecnomotor.thanos.database.menu.dao.*
import br.com.tecnomotor.thanos.database.menu.entity.*

@Database(
    entities = arrayOf(
        MontadoraEntity::class,
        VeiculoEntity::class,
        MotorizacaoEntity::class,
        TipoSistemaEntity::class,
        SistemaEntity::class,
        ConectorEntity::class,
        IdiomaEntity::class,
        PaisEntity::class,
        PlataformaEntity::class,
        UndercarEntity::class,
        VersaoEntity::class,
        AplicacaoEntity::class,
        AplicacaoConectorEntity::class,
        FuncaoModuloEntity::class,
        PaisAplicacaoEntity::class,
        PlataformaAplicacaoEntity::class,
        TipoFuncaoModuloEntity::class,
        UndercarFuncaoEntity::class,
        VersaoAplicacaoEntity::class
    ),
    version = 1
)
abstract class MenuDatabase : RoomDatabase() {

    abstract fun montadoraDao(): MontadoraDao
    abstract fun veiculoDao(): VeiculoDao
    abstract fun motorizacaoDao(): MotorizacaoDao
    abstract fun tipoSistemaDao(): TipoSistemaDao
    abstract fun sistemaDao(): SistemaDao
    abstract fun conectorDao(): ConectorDao
    abstract fun idiomaDao(): IdiomaDao
    abstract fun paisDao(): PaisDao
    abstract fun plataformaDao(): PlataformaDao
    abstract fun undercarDao(): UndercarDao
    abstract fun versaoDao(): VersaoDao
    abstract fun aplicacaoDao():AplicacaoDao
    abstract fun aplicacaoConectorDao(): AplicacaoConectorDao
    abstract fun funcaoModuloDao(): FuncaoModuloDao
    abstract fun paisAplicacaoDao(): PaisAplicacaoDao
    abstract fun plataformaAplicacaoDao(): PlataformaAplicacaoDao
    abstract fun tipoFuncaoModuloDao(): TipoFuncaoModuloDao
    abstract fun undercarFuncaoDao(): UndercarFuncaoDao
    abstract fun versaoAplicacaoDao(): VersaoAplicacaoDao

    companion object { // singleton
        @Volatile private var instance: MenuDatabase? = null

        //Singleton instance
        fun getInstance(context: Context? = null): MenuDatabase {
            if (instance == null)
                if(context == null)
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
        private fun buildDatabase(context: Context): MenuDatabase {
            return Room.databaseBuilder(context, MenuDatabase::class.java, MENU_DATABASE_NAME)
                .createFromAsset(DATABASE_PATH + MENU_DATABASE_NAME)
//                .allowMainThreadQueries()
//                .addMigrations(MenuMigrations.MIGRATION_1_2)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
