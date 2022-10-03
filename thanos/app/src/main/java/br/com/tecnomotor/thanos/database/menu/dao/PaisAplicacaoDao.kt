package br.com.tecnomotor.thanos.database.menu.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import br.com.tecnomotor.thanos.database.BaseDao
import br.com.tecnomotor.thanos.database.menu.entity.PaisAplicacaoEntity

@Dao
interface PaisAplicacaoDao : BaseDao<PaisAplicacaoEntity> {
    @Query("SELECT * FROM PAIS_APLICACAO")
    fun all(): LiveData<MutableList<PaisAplicacaoEntity>>
}